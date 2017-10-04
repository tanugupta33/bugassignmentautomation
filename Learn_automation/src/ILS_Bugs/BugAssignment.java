package ILS_Bugs;


public class BugAssignment {
	
	
	private static String from 	= "tgupta@yodlee.com";
	private static String to[] 	= {"tgupta@yodlee.com"};
	private static String cc[] 	= {"tgupta@yodlee.com"};
	//private static String to[] 	= {"IAE-TTR-Team-I18N@yodlee.com"};	
	private static String ANZlocaleName="ANZ";
	private static String UKlocaleName="UK";
	
	public static void main(String[] args) throws Exception{
		
		String aunz_tables[]=new String[3];
		aunz_tables[0]=BugDAO.getBugTable(ANZlocaleName);
		aunz_tables[1]=BugDAO.getDeveloperWiseCountTable(ANZlocaleName);
		aunz_tables[2]=BugDAO.getAgentWiseCountTable(ANZlocaleName);
		
		MailService.composeAndSendEMail(to, cc, from, aunz_tables,UKlocaleName);
		
		String ukTables[]=new String[3];
		ukTables[0]=BugDAO.getBugTable(UKlocaleName);
		ukTables[1]=BugDAO.getDeveloperWiseCountTable(UKlocaleName);
		ukTables[2]=BugDAO.getAgentWiseCountTable(UKlocaleName);
		MailService.composeAndSendEMail(to, cc, from, ukTables,UKlocaleName);
		
		
		System.out.println("mail sent successfully");
		
	}
}
