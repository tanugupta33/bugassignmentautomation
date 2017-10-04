package ILS_Bugs;

import java.util.Calendar;
import java.util.Date;

public class Utilities {

	
	public static Date generateSLA(Date createdOn, Integer idleTime) {
		return getDerrivedSLA(createdOn, idleTime);
	}
	
	private static Date getDerrivedSLA(Date createdOn, Integer idleTime) {

		Integer noOfDayToAdd = 4+idleTime;
		Date referenceDate = createdOn;
		boolean isCreatedOnSunday = false;
		if(referenceDate.getDay()==0){
			isCreatedOnSunday = true;
		}
		boolean isDateAlreadyIncreamented = false;
		for(int i = 1;i<=noOfDayToAdd;i++){
			if(referenceDate.getDay()==0){
				isDateAlreadyIncreamented = true;
				Calendar startCal = Calendar.getInstance();
				startCal.setTime(referenceDate);
//				System.out.println("from : "+referenceDate);
				startCal.add(Calendar.DATE, 1);
				referenceDate = startCal.getTime();
//				System.out.println("to : "+referenceDate);
			}
			if(referenceDate.getDay()==1
					||referenceDate.getDay()==2||referenceDate.getDay()==3||referenceDate.getDay()==4){
				isDateAlreadyIncreamented = true;
				Calendar startCal = Calendar.getInstance();
				startCal.setTime(referenceDate);
//				System.out.println("from : "+referenceDate);
				startCal.add(Calendar.DATE, 1);
				referenceDate = startCal.getTime();
//				System.out.println("to : "+referenceDate);
			}
			if(referenceDate.getDay()==5){
				if(!isDateAlreadyIncreamented){
					if(isCreatedOnSunday){
						isCreatedOnSunday = false;
						continue;
					}
					Calendar startCal = Calendar.getInstance();
					startCal.setTime(referenceDate);
//					System.out.println("from : "+referenceDate);
					startCal.add(Calendar.DATE, 3);
					referenceDate = startCal.getTime();
				}
			}
			if(referenceDate.getDay()==6){
				isDateAlreadyIncreamented = true;
				Calendar startCal = Calendar.getInstance();
				startCal.setTime(referenceDate);
				startCal.add(Calendar.DATE, 2);
				referenceDate = startCal.getTime();
			}
			isDateAlreadyIncreamented = false;
		}
		return referenceDate;
	
	}
	
	public static Integer myGetWorkingDaysBetweenTwoDates(Date startDate, Date endDate,Integer idleTime){

		return getWorkingDays(startDate, endDate, idleTime);
	}
	
	private static Integer getWorkingDays(Date startDate, Date endDate,Integer idleTime){

		if(startDate.getDate()==endDate.getDate()
				&&startDate.getMonth()==endDate.getMonth()
				&&startDate.getYear()==endDate.getYear()){
			if(!(startDate.getDay()!=6&&startDate.getDay()!=0)){
		    	return 0;
		    }else{
		    	return 1;
		    }
		}
		int netWorkingDays = 0;
		Date referenceDate = startDate;
		while(referenceDate.before(endDate)){
			Calendar startCal = Calendar.getInstance();
		    startCal.setTime(referenceDate);
		    startCal.add(Calendar.DATE, 1);
		    if(referenceDate.getDay()!=6&&referenceDate.getDay()!=0){
		    	//System.out.println(referenceDate);
		    	netWorkingDays++;
		    }
		    referenceDate = startCal.getTime();
		}
		return netWorkingDays-idleTime;
	}
		
	
}
