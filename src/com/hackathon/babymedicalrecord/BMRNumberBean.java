package com.hackathon.babymedicalrecord;

import java.io.Serializable;

public class BMRNumberBean
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String average;
  private Data[] datas;

  public Data[] constructArrays(int paramInt)
  {
    if (paramInt <= 0)
      return null;
    return new Data[paramInt];
  }

  public String[] convertKey()
  {
    String[] arrayOfString = new String[this.datas.length];
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfString.length)
        return arrayOfString;
      arrayOfString[i] = this.datas[i].key;
    }
  }

  public double[] convertValues()
  {
    double[] arrayOfDouble = new double[this.datas.length];
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfDouble.length)
        return arrayOfDouble;
      arrayOfDouble[i] = this.datas[i].num;
    }
  }

  public String getAverage()
  {
    return this.average;
  }

  public Data[] getDatas()
  {
    return this.datas;
  }

  public void setAverage(String paramString)
  {
    this.average = paramString;
  }

  public void setDatas(Data[] paramArrayOfData)
  {
    this.datas = paramArrayOfData;
  }

  public class Data
    implements Serializable
  {
    private static final long serialVersionUID = 1L;
    public String key;
    public int num;
    public double percent;

    public Data()
    {
    }

    public Data(String key, int num, double percent)
    {
      this.key = key;
      this.num = num;
      this.percent = percent;
    }
  }
}

