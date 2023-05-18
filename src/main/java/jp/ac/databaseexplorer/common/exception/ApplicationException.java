package jp.ac.databaseexplorer.common.exception;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class ApplicationException extends Exception{

  public ApplicationException(String errorCode,String errorMessage,Exception errorException){
    this.setErrorCode(errorCode);
    this.setErrorMessage(errorMessage);
    this.setErrorException(errorException);
  }
  private String errorCode;
  private String errorMessage;
  private Exception errorException;

}
