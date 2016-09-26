package com.wemolian.app.utils;

import java.text.SimpleDateFormat;
import java.text.ParseException; 
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 计算时间差
 * 日期比较差值不包括起始日期,包括最后日期 
 *
 * 用法 ：
 * <h1>DateCalculate dateCalculate </h1>
 * <h1>                   = DateCalculate.calculate("2011年03月17日", "2012年02月13日");</h1>  
 * <h1>System.out.println("月差为: " + dateCalculate.getDifferenceOfMonths());  </h1>
 * <h1>System.out.println("天差为: " + dateCalculate.getDifferenceOfDays());  </h1>
 * 
 * @author zhangyun
 */
public class DateCalculate {
	      
	    private long differenceOfMonths;//月份差值  
	    private long differenceOfDays;//天数差值  
	    private long differenceOfHours;//时差  
	    private long differenceOfMinutes;//时差  
	    
	      
	    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");  
	      
	    public static DateCalculate calculate(String startDate){  
	        try {  
	        	Date now = new Date();
	            return calculate(dateFormat.parse(startDate),now);  
	        } catch (ParseException e) { 
	        	System.out.println(1);
//	            e.printStackTrace();  
	        }  
	        return null;  
	    }  
	      
	    /** 
	     * 计算差值,注意 endDate > startDate 
	     * @param startDate 
	     * @param endDate 
	     * @return 
	     */  
	    public static DateCalculate calculate(Date startDate, Date endDate){  
	        if(startDate.after(endDate)) return null;  
//	        System.out.println("开始日：" + dateFormat.format(startDate) + ", 结束日: "+ dateFormat.format(endDate));  
	        DateCalculate dataCalculate = new DateCalculate();  
	          
	        Calendar firstDay = Calendar.getInstance();  
	        Calendar lastDay = Calendar.getInstance();  
	        firstDay.setTime(startDate);  
	        lastDay.setTime(endDate);  
	          
	        //算出天数总差值  
	        long allDays = (long) (((lastDay.getTimeInMillis()) - (firstDay.getTimeInMillis()))/(1000*24*60*60) +0.5);  
	        //计算时差
	        long allHours = ((lastDay.getTimeInMillis()) - (firstDay.getTimeInMillis()))/(1000*60*60);
	        //计算月差
	        long allMonth = ((lastDay.getTimeInMillis()) - (firstDay.getTimeInMillis()))/(1000*60*60*24*30);
	        //计算分钟差
	        long allMinutes = ((lastDay.getTimeInMillis()) - (firstDay.getTimeInMillis()))/(1000*60);
	        
	        Calendar loopEndDay = calculateLoopEndOfDate(firstDay,lastDay);  
//	        System.out.println("循环终止日期 : " + dateFormat.format(loopEndDay.getTime()));  
	          
	        dataCalculate.setDifferenceOfDays(0);  
	        dataCalculate.setDifferenceOfMonths(0);
	        dataCalculate.setDifferenceOfHours(0);
	        dataCalculate.setDifferenceOfMinutes(0);
	        dataCalculate.setDifferenceOfMinutes(allMinutes);
	        
	        dataCalculate.setDifferenceOfHours(allHours);
	          
//	        int month = firstDay.get(Calendar.MONTH);  
//	        while(!firstDay.equals(loopEndDay)){  
//	            firstDay.add(Calendar.DAY_OF_MONTH, 1);  
//	            allDays--;  
//	            if(month != firstDay.get(Calendar.MONTH)){  
//	                month = firstDay.get(Calendar.MONTH);  
//	                dataCalculate.setDifferenceOfMonths(dataCalculate.getDifferenceOfMonths()+1);  
//	            }  
//	        }  
	        dataCalculate.setDifferenceOfDays(allDays);
	        dataCalculate.setDifferenceOfMonths(allMonth);
	        
	        return dataCalculate;  
	          
	    }  
	  
	    /** 
	     * 计算循环终止日期 
	     * 例如:开始日：2016/03/17    结束日 2016/10/13 ,循环终止日期 2016/9/17; 
	     * @param startDate 
	     * @param endDate 
	     * @return 
	     */  
	    private static Calendar calculateLoopEndOfDate(Calendar startDate, Calendar endDate) {  
	        int year = endDate.get(Calendar.YEAR);  
	        int month = endDate.get(Calendar.MONTH);  
	        int day = startDate.get(Calendar.DAY_OF_MONTH);  
	        int maxDaysInMonth = getMaxDaysOfMonth(new GregorianCalendar(year,month,1));  
	          
	        if(year > startDate.get(Calendar.YEAR)){  
	            if(month == Calendar.JANUARY){  
	                year -= 1;  
	                month = Calendar.DECEMBER;  
	            }else{  
	                if(day > maxDaysInMonth){  
	                    month -= 1;  
	                    endDate.set(year, month, 1);  
	                    day = getMaxDaysOfMonth(new GregorianCalendar(year,month,1));  
	                }else{  
	                	int s = endDate.get(Calendar.DAY_OF_MONTH);
	                    if(day > endDate.get(Calendar.DAY_OF_MONTH)){  
	                        month -= 1;  
	                        endDate.set(year, month, 1);  
	                        maxDaysInMonth = getMaxDaysOfMonth(new GregorianCalendar(year,month,1));;  
	                        if(day > maxDaysInMonth){  
	                            day = maxDaysInMonth;  
	                        }  
	                    }  
	                }  
	            }  
	        }else{  
	            if(day > maxDaysInMonth){  
	                month -= 1;  
	                endDate.set(year, month, 1);  
	                day = getMaxDaysOfMonth(new GregorianCalendar(year,month,1));  
	            }else{  
	                if(day > endDate.get(Calendar.DAY_OF_MONTH)){  
	                    month -= 1;  
	                    endDate.set(year, month, 1);  
	                    maxDaysInMonth = getMaxDaysOfMonth(new GregorianCalendar(year,month,1));  
	                    if(day > maxDaysInMonth){  
	                        day = maxDaysInMonth;  
	                    }  
	                }  
	            }  
	        }  
	          
	        return new GregorianCalendar(year, month, day);  
	    }  
	  
	    /** 
	     * 获取一月最大天数,考虑年份是否为润年 
	     * @param date 
	     * @return 
	     */  
	    private static int getMaxDaysOfMonth(GregorianCalendar date) {  
	        int month = date.get(Calendar.MONTH);  
	        int maxDays  = 0;  
	        switch(month){  
	            case Calendar.JANUARY:  
	            case Calendar.MARCH:  
	            case Calendar.MAY:  
	            case Calendar.JULY:  
	            case Calendar.AUGUST:  
	            case Calendar.OCTOBER:  
	            case Calendar.DECEMBER:  
	            maxDays = 31;  
	            break;  
	            case Calendar.APRIL:  
	            case Calendar.JUNE:  
	            case Calendar.SEPTEMBER:  
	            case Calendar.NOVEMBER:  
	            maxDays = 30;  
	            break;  
	            case Calendar.FEBRUARY:  
	            if(date.isLeapYear(date.get(Calendar.YEAR))){  
	                maxDays = 29;  
	            }else{  
	                maxDays = 28;  
	            }  
	            break;  
	        }  
	        return maxDays;  
	    }  
	  
	    public long getDifferenceOfMonths() {  
	        return differenceOfMonths;  
	    }  
	  
	    public void setDifferenceOfMonths(long differenceOfmonths) {  
	        this.differenceOfMonths = differenceOfmonths;  
	    }  
	  
	    public long getDifferenceOfDays() {  
	        return differenceOfDays;  
	    }  
	  
	    public void setDifferenceOfDays(long differenceOfDays) {  
	        this.differenceOfDays = differenceOfDays;  
	    }

		public long getDifferenceOfHours() {
			return differenceOfHours;
		}

		public void setDifferenceOfHours(long differenceOfHours) {
			this.differenceOfHours = differenceOfHours;
		}  
	    
		
		
		
		public long getDifferenceOfMinutes() {
			return differenceOfMinutes;
		}

		public void setDifferenceOfMinutes(long differenceOfMinutes) {
			this.differenceOfMinutes = differenceOfMinutes;
		}

		public String getTime(){
			String returnStr = "";
			if (differenceOfMonths > 0) {
				if(differenceOfMonths > 12){
					int year = (int) (differenceOfMonths/12);
					returnStr =  year+ "年前"; 
				}else{
					returnStr = differenceOfMonths + "个月前";
				}
				return returnStr;
			}
			else if(differenceOfMonths == 0 && differenceOfDays > 0 ){
				return differenceOfDays + "天前";
			}
			else if(differenceOfDays == 0 && differenceOfHours > 0){
				return differenceOfHours + "小时前";
			}else{
				return differenceOfMinutes + "分钟前";
			}
			
		}
}
