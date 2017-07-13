/**
A class to create the peer reviewers for the submission in the same group
Copyright (C) Juli 2013 Jia Jiyou. PKU CN.
All rights reserved.
static groups_members.csv: contains: groupID, memberID
21,890

dynamic workshop_submissions.csv, from mdl_workshop_submissions, contains: submissionID, authorID

Create submission_reviewers.csv, can be uploaded into the DB
*/
import java.io.*;
import java.util.*;

/**
 * Read the csv file for the vocabulary of a given English term, and produce multichoice and cloze questions for every unit and for the whole term.
 * @author Jia Jiyou BJ 2012.1.27
 * @version 1.0
 */

public class Workshop
{

public static void main(String [] args)
	{
		
	 try{
		String line, submisson_reviewers;
        int count=70;
	submisson_reviewers="id,submissionid,reviewerid,weight,timecreated,timemodified,grade,gradinggrade,gradinggradeover,gradinggradeoverby,feedbackauthor,feedbackauthorformat,feedbackauthorattachment,feedbackreviewer,feedbackreviewerformat\n";

		HashMap <String, ArrayList> group_members=new HashMap();
	  BufferedReader fileIn = new BufferedReader(new FileReader("mdl_groups_members.csv")) ;
        /*groupid,userid
		   21,890
		*/
	while((line=fileIn.readLine())!=null)
        {
        String [] ss = line.trim().split(",");
		ArrayList <String> group_member=new ArrayList();
		if( group_members.containsKey(ss[1]))
		     group_member= (ArrayList <String>) (group_members.get(ss[1]));
		group_member.add(ss[2]);
		group_members.put(ss[1], group_member);	
		
		} //end of while
		System.out.println(group_members.size()+" groups");
		
		Set<String> groups= group_members.keySet();
 	 		
		fileIn = new BufferedReader(new FileReader("mdl_workshop_submissions.csv")) ;
		HashMap <String, String> submission_author=new HashMap();
		HashMap <String, String> author_submission=new HashMap();

	  while((line=fileIn.readLine())!=null)
        {
        String [] ss = line.trim().split(",");
        //System.out.println( line);
		submission_author.put(ss[0], ss[3]);
		author_submission.put(ss[3], ss[0]);
				
	 } //end of while
	 fileIn.close();
	 System.out.println( submission_author.size()+" submissions.");
	
	Set<String> submissions= submission_author.keySet();
 	 
	for (String submission: submissions)
		{String author=submission_author.get(submission);
		for(String group: groups){
	 	 ArrayList <String> members= group_members.get(group);//members in a group
	 	System.out.println(members);
	 	 if(members.contains(author))//the group with the author
		 {
			 System.out.println(author);
			 for(String member: members)
			  { 
				 if(!member.equals(author))  //the other members
				{
				  if(author_submission.containsKey(member)) //the other member has also a submission
				  submisson_reviewers+=(count++)+","
						  	+submission+","
						  	+member+
						  	",1,1482129618,0,,,,,,1,0,,1\n";
				}
			  }
		 }
			 
		}
		}
	System.out.println( submisson_reviewers);      
	FileWriter fw = new FileWriter("C:\\Users\\Administrator\\Desktop\\mdl_submission_assesments.csv", true);
	fw.write(submisson_reviewers);
	fw.close();
 }
 catch (Exception e){e.printStackTrace();}
}		//end of main
}

	/*
	Set<String> units= unit_choice_vocabulary.keySet();
 	 for (String unit: units)
	 {
	 	 ArrayList <Word_Phrase> wps= unit_choice_vocabulary.get(unit);
		 create_choice_file(wps, dir, unit);
		 wp_choice.put(unit, wps);
     }
 */
 
