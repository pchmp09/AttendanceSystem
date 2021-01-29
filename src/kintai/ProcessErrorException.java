package model;
//本システムで定義した独自例外です。
//  Exception の子クラスですので、チェックされる例外です。
public class ProcessErrorException extends Exception {
  public ProcessErrorException(String message) {
    super(message);
  }
}

