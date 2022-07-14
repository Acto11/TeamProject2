package com.example.teamproject1;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.RequiresApi;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

public class CalendarDecorator implements DayViewDecorator {

 private HashSet<CalendarDay> dates = new HashSet<>();


 public CalendarDecorator(Collection<CalendarDay> dates) {
  this.dates = new HashSet<>(dates);


 }

 @Override
 public boolean shouldDecorate(CalendarDay day) {
  return dates.contains(day);
 }

 @Override
 public void decorate(DayViewFacade view) {
 view.addSpan(new ForegroundColorSpan(Color.RED));
 }
}