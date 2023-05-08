package com.databaseexplorer.api.controller.response;

import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class CPUData {

  @NonNull
  private Date time;

  @NonNull
  private double usage;

}
