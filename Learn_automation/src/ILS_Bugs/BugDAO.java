package ILS_Bugs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BugDAO {
	private static String DATE_FORMAT_DATE 		= "yyyy-MM-dd";
	private static SimpleDateFormat sdfDate 	= new SimpleDateFormat(DATE_FORMAT_DATE);
	private static String DATE_FORMAT_STRING 	= "dd-MM-yyyy";
	private static SimpleDateFormat sdfString 	= new SimpleDateFormat(DATE_FORMAT_STRING);
	private static HashMap<String, Integer> ANZ_developerList=new HashMap<String, Integer>();
	private static HashMap<String, Integer> UK_developerList=new HashMap<String, Integer>();
	
	private static final String ANZ_BUGQUERY = "select\n" +
			"ba.login_name,\n" +
			"b.bug_id,\n" +
			"b.cf_agents,\n" +
			"b.creation_ts,\n" +
			"b.delta_ts,\n" +
			"b.status_whiteboard,\n" +
			"b.cf_department,\n" +
			"b.cf_workflow_status,\n" +
			"b.cf_idle_time,\n" +
			"b.cf_customer\n" +
			"from bugs b, profiles ba\n" +
			"where b.assigned_to= ba.userid\n" +
			"and b.cf_bugtype='Bug'\n" +
			"and b.cf_department='IAE'\n" +
			"and b.cf_bugtype not in ('NSR')\n" +
			"and b.product_id = 53\n" +
			"and b.cf_agents in('AUNationalAustraliaBank','AUNationalAustraliaBankCC','AUVirginMoneyCC','AUVirginMoney','WestpacAUBankMobile','WestpacAUCreditsMobile','NZNationalBankCreditCard','NZNationalBankofNewZealand','IBank','StGeorgeCreditCardAU','AUCommonwealthBankCC','AUCitibank','AUCitibankCreditCards','AUANZEverydayBank','AUANZEverydayCC','NZBankNewZealand','NZBankNewZealandCC','AUBankWest','AUBankWestCredits','CUAAUBank','AUCommonwealthBank','AUBendigoBank','AUBendigoBankCreditCards','NZKiwibank','NZKiwibankCC','QueenslandAUCreditCard','QueenslandBankAU','WestpacNZ','WestpacNZCC','AUAmexCCMobile','AUHeritageBuildingSocietyBank','AUSuncorpMetway','AUWestpacCorporate','INGDirectAU','NewcastleAUBank','NewcastleAUCC','NZASBBank','SuncorpCreditCardAU','NZASBBankCredits')\n" +
			"and b.cf_backend_frontend not in ('Preventive Fixes')\n" +
			"and b.bug_status in ('NEW','ASSIGNED','REOPENED')\n" +
			"and b.cf_backend_frontend in ('Customer', 'VIP', 'Forum','CS','---')\n" +
			"and b.component_id in (1599,1697,1881,1931,1963,2187,2427,3215,3217,3439)\n" +
			"and b.creation_ts >='2016-12-19'\n" +
			"order by cf_customer;";
	
	private static final String UK_BUGQUERY = "select\n" +
			"ba.login_name,\n" +
			"b.bug_id,\n" +
			"b.cf_agents,\n" +
			"b.creation_ts,\n" +
			"b.delta_ts,\n" +
			"b.status_whiteboard,\n" +
			"b.cf_department,\n" +
			"b.cf_workflow_status,\n" +
			"b.cf_idle_time,\n" +
			"b.cf_customer\n" +
			"from bugs b, profiles ba\n" +
			"where b.assigned_to= ba.userid\n" +
			"and b.cf_bugtype='Bug'\n" +
			"and b.cf_department='IAE'\n" +
			"and b.cf_bugtype not in ('NSR')\n" +
			"and b.product_id = 53\n" +
			"and b.cf_agents in('UKMetroBank','UKMetroBankCredit','UKAbbeyNational','UKRoyalBankScotlandCC','HSBCBankCreditCardUK','UKMBNACreditsCustom','UKLloydsTSB','UKAbbeyNationalCC123','UKLloydsTSBCC','UKAmexCreditCard','UKRoyalBankScotland','UKHalifax','UKHalifaxcc','UKFirstDirectBank','UKHSBC','UKBarclaycard','UKBarclaysBasicAccess','UKFirstDirectBankCC','UKCooperativeBank','UKCooperativeBankCC','UKNationwideBuilding','UKNationwideBuildingCC','UKRoyalBankScotlandBusinessDigital','UKCapitalOne')\n" +
			"and b.cf_backend_frontend not in ('Preventive Fixes')\n" +
			"and b.bug_status in ('NEW','ASSIGNED','REOPENED')\n" +
			"and b.cf_backend_frontend in ('Customer', 'VIP', 'Forum','CS','---')\n" +
			"and b.component_id in (1599,1697,1881,1931,1963,2187,2427,3215,3217,3439)\n" +
			"and b.creation_ts >='2016-12-19'\n" +
			"order by cf_customer;";
	
	private static final String ANZ_AGENTWISEQUERY = "select\n" +
			"b.cf_agents,\n" +
			"count(b.bug_id)\n" +
			"from bugs b, profiles ba\n" +
			"where b.assigned_to= ba.userid\n" +
			"and b.cf_bugtype='Bug'\n" +
			"and b.cf_department='IAE'\n" +
			"and b.cf_bugtype not in ('NSR')\n" +
			"and b.product_id = 53\n" +
			"and b.cf_agents in('AUNationalAustraliaBank','AUNationalAustraliaBankCC','AUVirginMoneyCC','AUVirginMoney','WestpacAUBankMobile','WestpacAUCreditsMobile','NZNationalBankCreditCard','NZNationalBankofNewZealand','IBank','StGeorgeCreditCardAU','AUCommonwealthBankCC','AUCitibank','AUCitibankCreditCards','AUANZEverydayBank','AUANZEverydayCC','NZBankNewZealand','NZBankNewZealandCC','AUBankWest','AUBankWestCredits','CUAAUBank','AUCommonwealthBank','AUBendigoBank','AUBendigoBankCreditCards','NZKiwibank','NZKiwibankCC','QueenslandAUCreditCard','QueenslandBankAU','WestpacNZ','WestpacNZCC','AUAmexCCMobile','AUHeritageBuildingSocietyBank','AUSuncorpMetway','AUWestpacCorporate','INGDirectAU','NewcastleAUBank','NewcastleAUCC','NZASBBank','SuncorpCreditCardAU','NZASBBankCredits')\n" +
			"and b.cf_backend_frontend not in ('Preventive Fixes')\n" +
			"and b.bug_status in ('NEW','ASSIGNED','REOPENED')\n" +
			"and b.cf_backend_frontend in ('Customer', 'VIP', 'Forum','CS')\n" +
			"and b.component_id in (1599,1697,1881,1931,1963,2187,2427,3215,3217,3439)\n" +
			"and b.creation_ts >='2016-12-19'\n" +
			"group by b.cf_agents\n" +
			"order by cf_customer;";
	
	private static final String UK_AGENTWISEQUERY = "select\n" +
			"b.cf_agents,\n" +
			"count(b.bug_id)\n" +
			"from bugs b, profiles ba\n" +
			"where b.assigned_to= ba.userid\n" +
			"and b.cf_bugtype='Bug'\n" +
			"and b.cf_department='IAE'\n" +
			"and b.cf_bugtype not in ('NSR')\n" +
			"and b.product_id = 53\n" +
			"and b.cf_agents in('UKMetroBank','UKMetroBankCredit','UKAbbeyNational','UKRoyalBankScotlandCC','HSBCBankCreditCardUK','UKMBNACreditsCustom','UKLloydsTSB','UKAbbeyNationalCC123','UKLloydsTSBCC','UKAmexCreditCard','UKRoyalBankScotland','UKHalifax','UKHalifaxcc','UKFirstDirectBank','UKHSBC','UKBarclaycard','UKBarclaysBasicAccess','UKFirstDirectBankCC','UKCooperativeBank','UKCooperativeBankCC','UKNationwideBuilding','UKNationwideBuildingCC','UKRoyalBankScotlandBusinessDigital','UKCapitalOne')\n" +
			"and b.cf_backend_frontend not in ('Preventive Fixes')\n" +
			"and b.bug_status in ('NEW','ASSIGNED','REOPENED')\n" +
			"and b.cf_backend_frontend in ('Customer', 'VIP', 'Forum','CS')\n" +
			"and b.component_id in (1599,1697,1881,1931,1963,2187,2427,3215,3217,3439)\n" +
			"and b.creation_ts >='2016-12-19'\n" +
			"group by b.cf_agents\n" +
			"order by cf_customer;";
	
	
	
	public static Statement stmt 	= null;
	
	/*
	 * Get list of bugs
	 */
	public static String getBugTable(String locale) throws Exception{
		return getBugTableList(locale);
	}
	
	private static String getBugTableList(String locale) throws Exception{
		System.out.println("current locale: " + locale);
		System.out.println(" ++ Query Execution Started ++ ");
		int bugCount = 0;
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		Connection conn=DBConnection.establishMySQLConnection();
		stmt = conn.createStatement();
		
		ResultSet queryResult= null;
		if(locale=="ANZ"){
			queryResult= stmt.executeQuery(ANZ_BUGQUERY);
		}
		if(locale=="UK"){
			queryResult= stmt.executeQuery(UK_BUGQUERY);
		}
		Map<String,List> devMap=new HashMap<String, List>();
		devMap.put("tgupta", Arrays.asList("AUVirginMoney","AUVirginMoneyCC","NZBankNewZealand","NZBankNewZealandCC","WestpacAUBankMobile","WestpacAUCreditsMobile","WestpacNZ","WestpacNZCC"));
		devMap.put("sjain8", Arrays.asList("NZASBBank","NZASBBankCredits","AUAmexCCMobile","AUBankWest","AUBankWestCredits","IBank","QueenslandAUCreditCard","QueenslandBankAU","StGeorgeCreditCardAU"));
		devMap.put("sshankar", Arrays.asList("AUANZEverydayBank","AUANZEverydayCC","AUNationalAustraliaBank","AUNationalAustraliaBankCC","NZKiwibank","NZKiwibankCC"));
		devMap.put("nchandra", Arrays.asList("AUBendigoBank","AUBendigoBankCreditCards","AUSuncorpMetway","CUAAUBank","INGDirectAU","NewcastleAUBank","NewcastleAUCC","SuncorpCreditCardAU","NZNationalBankCreditCard","NZNationalBankofNewZealand"));
		devMap.put("rbiswal", Arrays.asList("AUHeritageBuildingSocietyBank","AUCitibank","AUCitibankCreditCards","AUCommonwealthBank","AUCommonwealthBankCC","AUWestpacCorporate"));
		devMap.put("aparna", Arrays.asList("UKMetroBank","UKMetroBankCredit","UKAbbeyNational","UKRoyalBankScotlandCC","HSBCBankCreditCardUK","UKMBNACreditsCustom","UKLloydsTSB","UKAbbeyNationalCC123","UKLloydsTSBCC","UKAmexCreditCard","UKRoyalBankScotland","UKHalifax","UKHalifaxcc","UKFirstDirectBank","UKHSBC","UKBarclaycard","UKBarclaysBasicAccess","UKFirstDirectBankCC","UKCooperativeBank","UKCooperativeBankCC","UKNationwideBuilding","UKNationwideBuildingCC","UKRoyalBankScotlandBusinessDigital","UKCapitalOne"));
		String mailContent="<div><b>Bug Assignment Table</b><table><tr style=\"background-color:DarkBlue; color:white; font-style:bold\">"
				+ "<th>Sr No</th><th>	Bug ID</th><th>	ClassName</th><th>	Created	</th><th>Updated	</th><th>Department	</th><th>"
				+ "Workflow Status	</th><th>Customer	</th><th>Age	</th><th>ETA</th><th>	Developer</th>"
				+ "</tr>";
		Date todayDate = new Date();
		while(queryResult.next()){
			
			mailContent+="<tr>";
			
			bugCount++;
			mailContent+="<td>"+bugCount+"</td>";
		
			Integer bugID = Integer.parseInt(queryResult.getString("bug_id"));
			mailContent+="<td>"
					+"<a href= https://blrbugzilla.yodlee.com/show_bug.cgi?id="+bugID+">"+bugID+"</a>"
					+"</td>";
			
			String agentName = queryResult.getString("cf_agents");
			mailContent+="<td>"+agentName+"</td>";
			
			String createdDate = queryResult.getString("creation_ts");
			createdDate = createdDate.substring(0,createdDate.indexOf(" "));
			Date createdOn = sdfDate.parse(createdDate);
			createdDate = sdfString.format(createdOn);
			mailContent+="<td>"+createdDate+"</td>";
			
			String changedDate = queryResult.getString("delta_ts");
			changedDate = changedDate.substring(0,changedDate.indexOf(" "));
			mailContent+="<td>"+changedDate+"</td>";
			
			String department = queryResult.getString("cf_department");
			mailContent+="<td>"+department+"</td>";
			
			String workflowStatus = queryResult.getString("cf_workflow_status");
			mailContent+="<td>"+workflowStatus+"</td>";
			
			String customer = queryResult.getString("cf_customer");
			mailContent+="<td>"+customer+"</td>";
			
			String idleTimeString = queryResult.getString("cf_idle_time");
			
			Integer idleTime = 0;
			if(idleTimeString.isEmpty()){
				idleTime = 0;
			}else{
				idleTime = Integer.parseInt(idleTimeString);
			}
			
			Integer age = Utilities.myGetWorkingDaysBetweenTwoDates(createdOn,todayDate,idleTime);
			mailContent+="<td>"+age+"</td>";
			
			Date sla = Utilities.generateSLA(createdOn,idleTime);
			String slaDate = sdfString.format(sla);
			mailContent+="<td>"+slaDate+"</td>";
			
			String loginName = queryResult.getString("login_name");
			
			for(String dev:devMap.keySet()){
			if(devMap.get(dev).contains(agentName)){
				loginName= dev;
				break;
			}
			}
			
			if(developerList.containsKey(loginName)){
				developerList.put(loginName, developerList.get(loginName)+1);
			}else{
				developerList.put(loginName, 1);
			}
			mailContent+="<td>"+loginName+"</td>";
		
		}
		mailContent+="</tr></table></div>";
		return mailContent;
	
	}
	
	
	/*
	 * Get list of developer with count
	 * Dependent on function 
	 * String getBugTable()
	 * as we are getting list of developers with bug count from there only
	 */
	public static String getDeveloperWiseCountTable(String locale) throws Exception{
		return getDeveloperWiseCountList(locale);
	}
	
	private static String getDeveloperWiseCountList(String locale) throws Exception{

		Set<String> keySet=developerList.keySet();
		Iterator<String> key=keySet.iterator();
		System.out.println(" ++ Generating developerWiseCount ++ ");
		String mailContent="<div><b>Individual contribution on bugs </b><table name=\"developerWiseCountTable\">"
				+ "<tr style=\"background-color:DarkBlue; color:white; font-style:bold\">"
				+ "<th>Assignee</th>"
				+ "<th>Bug Count</th>"
				+ "</tr>";
		while(key.hasNext()){
			String developer=key.next();
			int count=developerList.get(developer);
			mailContent=mailContent+"<tr><td>"+developer+"</td><td>"+count+"</td></tr>";			
		}
		mailContent=mailContent+"</table></div>";
		return mailContent;
		
	
	}
	
	/*
	 * Get agent wise count
	 */
	public static String getAgentWiseCountTable(String locale) throws Exception{
		return getAgentWiseCountTableData(locale);
	}
	
	private static String getAgentWiseCountTableData(String locale) throws Exception{

		System.out.println(" ++ Query Execution Started ++ ");
		int agentCount=0;
		Connection conn=DBConnection.establishMySQLConnection();
		stmt = conn.createStatement();
		
		ResultSet rs=null;
		if(locale=="ANZ"){
			rs=stmt.executeQuery(ANZ_AGENTWISEQUERY);
		}
		if(locale=="UK"){
			rs=stmt.executeQuery(UK_AGENTWISEQUERY);
		}
					
		String mailContent="<div><b>Agent wise bug count</b><table name=\"developerWiseCountTable\">"
				+ "<tr style=\"background-color:DarkBlue; color:white; font-style:bold\">"
				+ "<th>Sr No.</th>"
				+ "<th>AgentName</th>"
				+ "<th>Bug Count</th>"
				+ "</tr>";
		while(rs.next()){
			agentCount++;
			mailContent+="<tr>";
			mailContent+="<td>"+agentCount+"</td>";
			mailContent=mailContent+"<td>"+rs.getString(1)+"</td><td>"+rs.getInt(2)+"</td>";			
		}
		mailContent=mailContent+"</tr></table></div>";
		return mailContent;

	
	}
	
}
