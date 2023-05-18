package jp.ac.databaseexplorer.common.exception;

import lombok.Data;

@Data
public class SystemException extends RuntimeException{

  public SystemException(String errorCode,String errorMessage,Exception errorException){
    this.setErrorCode(errorCode);
    this.setErrorMessage(errorMessage);
    this.setErrorException(errorException);
  }
  private String errorCode;
  private String errorMessage;
  private Exception errorException;
}
