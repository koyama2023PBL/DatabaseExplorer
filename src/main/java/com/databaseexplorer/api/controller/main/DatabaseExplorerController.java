package com.databaseexplorer.api.controller.main;


import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping(value = "/api/research")
public class DatabaseExplorerController {

    /*
    コンストラクタ。
    起動時の処理として解析対象のDBへ接続し、継続的に情報を取得するサービスを非同期で実行する。
     */
    @PostConstruct
    public void controllerConstructor(){

    }


    @RequestMapping(value = "/healthCheck")
    @GetMapping
    public ResponseEntity<?> healthCheck() {
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
