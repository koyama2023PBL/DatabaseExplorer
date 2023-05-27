package jp.ac.databaseexplorer.common.component.os;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import jakarta.annotation.PreDestroy;
import jp.ac.databaseexplorer.common.exception.SystemException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 監視対象のOSにSSHで接続し、コマンドを実行するユーティリティクラスです。
 */
@Component
public class SshUtil {

  /**
   * Session
   */
  private final Session session;

  /**
   * コンストラクタ
   * Sessionを初期化します。
   */
  public SshUtil(
      @Value("${os.data.async.collection.auth}")        String auth,
      @Value("${os.data.async.collection.username}")    String username,
      @Value("${os.data.async.collection.address}")     String host,
      @Value("${os.data.async.collection.password}")    String password,
      @Value("${os.data.async.collection.keyfile}")     String keyfile,
      @Value("${os.data.async.collection.passphrase}")  String passphrase,
      @Value("${os.data.async.collection.known_hosts}") String known_hosts
  ) {
    try {
      JSch jsch = new JSch();
      switch (auth) {
        case "password" -> {
          this.session = jsch.getSession(username, host);
          this.session.setPassword(password);
          this.session.setConfig("StrictHostKeyChecking", "no");
        }
        case "publickey" -> {
          jsch.addIdentity(keyfile, passphrase);
          jsch.setKnownHosts(known_hosts);
          this.session = jsch.getSession(username, host);
        }
        default -> throw new SystemException("", "", new Exception());
      }
      this.session.connect();
    } catch (SystemException se) {
      throw new SystemException("SYS-00015", "SSHの認証方式が不正です", se);
    } catch (JSchException jse) {
      throw new SystemException("SYS-00010", "SSHのセッション初期化に失敗しました", jse);
    } catch (Exception e) {
      throw new SystemException("SYS-00011", "SSHのセッション初期化時に予期せぬエラーが発生しました", e);
    }
  }

  /**
   * コマンドを実行し、結果を文字列で返却します。
   */
  public synchronized String execute(String command) {  // TODO: スレッド管理（今は@Asyncしてるので都度新しいスレッドが動く）
    try {
      ChannelExec channel = (ChannelExec) session().openChannel("exec");
      channel.setCommand(command);
      InputStream in = channel.getInputStream();
      channel.connect();
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      StringBuilder  output = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line).append("\n");
      }
      channel.disconnect();  // InputStreamもcloseされる。
      return output.toString();
    } catch (JSchException jse) {
      throw new SystemException("SYS-00012", "コマンド実行時にSSH接続エラーが発生しました", jse);
    } catch (IOException ioe) {
      throw new SystemException("SYS-00013", "コマンド実行結果の読み込みに失敗しました", ioe);
    } catch (Exception e) {
      throw new SystemException("SYS-00014", "コマンド実行時に予期せぬエラーが発生しました", e);
    }
  }

  /**
   * 有効なセッションを取得します。
   */
  private Session session() throws JSchException {
    if (!this.session.isConnected()) {
      this.session.connect();
    }
    return this.session;
  }

  /**
   * インスタンスが破棄される際はSessionを閉じます。
   */
  @PreDestroy
  public void cleanup() {
    if (this.session != null && this.session.isConnected()) {
      this.session.disconnect();
    }
  }
}
