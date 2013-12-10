package com.hackathon.babymedicalrecord;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import java.util.List;

public class BMRStatisticsPagerAdapter extends PagerAdapter
{
  private List<View> lists;

  public BMRStatisticsPagerAdapter(List<View> paramList)
  {
    this.lists = paramList;
  }

  public void destroyItem(View paramView, int paramInt, Object paramObject)
  {
    ((ViewPager)paramView).removeView((View)this.lists.get(paramInt));
  }

  public void finishUpdate(View paramView)
  {
  }

  public int getCount()
  {
    if (this.lists == null)
      return 0;
    return this.lists.size();
  }

  public Object instantiateItem(View paramView, int paramInt)
  {
    ((ViewPager)paramView).addView((View)this.lists.get(paramInt), 0);
    return this.lists.get(paramInt);
  }

  public boolean isViewFromObject(View paramView, Object paramObject)
  {
    return paramView == paramObject;
  }

  public void restoreState(Parcelable paramParcelable, ClassLoader paramClassLoader)
  {
  }

  public Parcelable saveState()
  {
    return null;
  }

  public void setLists(List<View> paramList)
  {
    this.lists = paramList;
    notifyDataSetChanged();
  }

  public void startUpdate(View paramView)
  {
  }
}
