package org.eclipse.jucmnav.grl.QAMM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright (C) 2020 Mawal Mohammed - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the Eclipse Public License - v 2.0 ,
 */

public class GRLMetricsCalculators {

	
boolean containsElement(int [] list, int element){
		 for (int i=0; i<list.length;i++)
			 if (list [i]==element)
				 return true;
		return false;
	}
	

boolean isItSource(int x, int [][] cons)	{
	
	for (int i=0;i<cons.length;i++){
		if(cons[i][0]==x)
			
			return Boolean.TRUE;
	}
	
	return Boolean.FALSE;
}

boolean isTragetsLocationsOutsideThisActor(int x,int [][] allConnections, List <Integer> allElementsInAnActor){
	int noOfTargets=0;
	for (int i=0;i<allConnections.length;i++){
		if(allConnections[i][0]==x)
			noOfTargets++;
	}
	int [] allTargets= new int [noOfTargets];
	int y=0;
	for (int i=0;i<allConnections.length;i++){
		if(allConnections[i][0]==x){
			allTargets[y]=allConnections[i][1];
			y++;}
	}
	for (int i=0;i<allTargets.length;i++){
		for (int j=0;j<allElementsInAnActor.size();j++){	
			if (allTargets[i]== allElementsInAnActor.get(j))
				return Boolean.FALSE;
		}
	}
	
	return Boolean.TRUE;
}

boolean testingIfNodeIsReferedByRefs(int node, String refs) {
	
	if(refs!=""){
		String[] integerStrings = refs.split(" "); 
		int [] allRefs = new int[integerStrings.length]; 
		for (int i = 0; i < allRefs.length; i++){
			allRefs[i] = Integer.parseInt(integerStrings[i]); 
		}
		for (int i = 0; i < allRefs.length; i++){
			if(allRefs[i]==node)
				return true;
		}
		
	}
		return false;
}


boolean containsLink(int lnk, int [] allDeps){
	for (int i=0; i<allDeps.length; i++){
		if(allDeps[i] == lnk)
			return Boolean.TRUE;
	}
	
	return Boolean.FALSE;
}

boolean allSourcesAndTargetsAreDependencies(int dependum, int [] allDeps, int [][] allCons){
	
	for (int i=0; i<allCons.length; i++)
		if ((allCons[i][0]==dependum || allCons[i][1]==dependum) && !containsLink( allCons[i][2],allDeps ))
			return Boolean.FALSE;
	
	
	
	return Boolean.TRUE;
}



boolean chosen (int element, List <Integer> allElementsInAnActor, boolean [] selectedElements){

for (int i = 0; i < allElementsInAnActor.size(); i++){
	if (element==allElementsInAnActor.get(i))
		return selectedElements[i];
}
return Boolean.FALSE;
}


boolean isItInSameActor(int element, int thisActor,List <Integer> listOfContRefsInActor,String [][] allIntentionalElementRefs){

for (int i=0; i<allIntentionalElementRefs.length;i++){
	
	if (Integer.parseInt(allIntentionalElementRefs[i][0])==element && allIntentionalElementRefs[i][1] != "")
		return listOfContRefsInActor.contains(Integer.parseInt(allIntentionalElementRefs[i][1]));
}
return false;
}

boolean isAllsourcesNotGoalsORSoftgoals( List <Integer> listOfSources , int [][] nodes, String [][] allElements  ) {
	int  nodeDef=-1;
	for (int i=0; i<listOfSources.size();i++ ) {
		for (int j=0;j<nodes.length;j++) {
			if (listOfSources.get(i)==nodes[j][0]) {
				nodeDef=nodes[j][1];
				break;
			}
		}
		for (int k=0; k<allElements.length;k++) {
			if (nodeDef == Integer.parseInt(allElements[k][0])) {
				 if ((allElements[k][1].equalsIgnoreCase("Goal") || allElements[k][1].equalsIgnoreCase(""))) {
					 return false;
				}
					
			}
		}
	}
	
		
	return true;
}

boolean isAllRefsOfAnElementsLowLevels(String elementRefs, List <Integer> listOfLowLevelGoalsAndSoftgoalsRefs ) {
	List <Integer> listOfElementRefs =  new ArrayList<Integer> ();
	String[] integerStringsOfElementRefs =  elementRefs.split(" "); 
	for (int j = 0; j < integerStringsOfElementRefs.length; j++){
		listOfElementRefs.add(Integer.parseInt(integerStringsOfElementRefs[j])); 
	}
	
	for (int j = 0; j < listOfElementRefs.size(); j++){
		if (! listOfLowLevelGoalsAndSoftgoalsRefs.contains(listOfElementRefs.get(j)))
				return false;
	}
	
	

	return true;
}


int strategicCompetencCalculator(List<Integer> listOfGoalsAndSoftgoalsRefs,int [][] allConnections,List<Integer> listOfNodesInActor, String [][] allElements,int [][] nodes){
	
	List<Integer> listOfLowLevelGoalsAndSoftgoalsRefs = new ArrayList<Integer> ();
	
	for (int n=0;n<listOfGoalsAndSoftgoalsRefs.size(); n++) {
		listOfLowLevelGoalsAndSoftgoalsRefs.add(listOfGoalsAndSoftgoalsRefs.get(n)); 
		}
	
	List<Integer> listOfSources = new ArrayList<Integer> ();
	
	 for (int n=0;n<listOfGoalsAndSoftgoalsRefs.size(); n++) {
		 int target=listOfGoalsAndSoftgoalsRefs.get(n);
		 listOfSources.clear();
		 for (int m=0;m<allConnections.length; m++) {
			
			if (target == allConnections[m][1] && listOfNodesInActor.contains(allConnections[m][0]))
				listOfSources.add(allConnections[m][0]);
				}
		 

		 if (!listOfSources.isEmpty()) {
			 if (!isAllsourcesNotGoalsORSoftgoals(listOfSources,nodes,allElements) ) {
				 int indexTarget=listOfLowLevelGoalsAndSoftgoalsRefs.indexOf(target);	
				 listOfLowLevelGoalsAndSoftgoalsRefs.remove(indexTarget);
				 }
		 }
	 }
	 
	 List<Integer> listOfLowLevelGoalsAndSoftgoals= new ArrayList<Integer> ();
	 
	 for (int e=0;e<allElements.length;e++) {
		 if (!allElements[e][2].equalsIgnoreCase(""))
		 if(isAllRefsOfAnElementsLowLevels(allElements[e][2],listOfLowLevelGoalsAndSoftgoalsRefs))
			 listOfLowLevelGoalsAndSoftgoals.add(Integer.parseInt(allElements[e][0]));
	 }

	
	
	return listOfLowLevelGoalsAndSoftgoals.size();
		
}



int depthOfRefinementCalculator(List <Integer> allElementsInAnActor, String [][] allElements, int [][] allConnections,String [][] allIntentionalElementRefs, List <Integer> listOfContRefsInActor,int thisActor ){
	
	int noOfIntelements =0;
	for (int i = 0; i < allElementsInAnActor.size(); i++){
		for (int j=0; j< allElements.length; j++){
			if (testingIfNodeIsReferedByRefs(allElementsInAnActor.get(i),allElements[j][1]) && ( allElements[j][0]=="" || allElements[j][0].equalsIgnoreCase("Goal") ||  allElements[j][0].equalsIgnoreCase("Ressource") || allElements[j][0].equalsIgnoreCase("Task")) )
				noOfIntelements++;
		}
	}
	
	int x=0;
	int [] intElementsInAnActor = new int [noOfIntelements]; 
	for (int i = 0; i < allElementsInAnActor.size(); i++){
		for (int j=0; j< allElements.length; j++){
			if (testingIfNodeIsReferedByRefs(allElementsInAnActor.get(i),allElements[j][1]) && ( allElements[j][0]=="" || allElements[j][0].equalsIgnoreCase("Goal") ||  allElements[j][0].equalsIgnoreCase("Ressource") || allElements[j][0].equalsIgnoreCase("Task")) ){
				intElementsInAnActor[x]=allElementsInAnActor.get(i);
				x++;
			}
		}
		
	}
		
	int noOfHighLevelintElementsInAnActor = 0;
	for (int i = 0; i < intElementsInAnActor.length; i++){
		if(!isItSource(intElementsInAnActor[i],allConnections))
			noOfHighLevelintElementsInAnActor++;
		else if (isItSource(intElementsInAnActor[i],allConnections) && isTragetsLocationsOutsideThisActor(intElementsInAnActor[i],allConnections,allElementsInAnActor ) )
			noOfHighLevelintElementsInAnActor++;
	}
	
	int [] highLevelIntElementsInActor=new int [noOfHighLevelintElementsInAnActor];
	int y=0;
	for (int i = 0; i < intElementsInAnActor.length; i++){
		if(!isItSource(intElementsInAnActor[i],allConnections)){
			highLevelIntElementsInActor[y]=intElementsInAnActor[i];
			y++;}
		else if (isItSource(intElementsInAnActor[i],allConnections) && isTragetsLocationsOutsideThisActor(intElementsInAnActor[i],allConnections,allElementsInAnActor )){
			highLevelIntElementsInActor[y]=intElementsInAnActor[i];
			y++;}
	}
	
	boolean [] selectedElements = new boolean [allElementsInAnActor.size()]; 
	Arrays.fill(selectedElements, Boolean.FALSE);
	
	int depth=1;
	int [] targets = new int [highLevelIntElementsInActor.length];
	
	for (int i=0; i<highLevelIntElementsInActor.length;i++){
		targets[i]=highLevelIntElementsInActor[i];
	}
	
	while ( targets.length>0){

		int noOfsources=0;
		for (int i=0; i<targets.length;i++){
			for (int j=0; j<allConnections.length;j++){
				if (allConnections[j][1]==targets[i] && containsElement(targets,allConnections[j][1]) && !chosen(allConnections[j][1], allElementsInAnActor, selectedElements)  && isItInSameActor(allConnections[j][0], thisActor, listOfContRefsInActor,allIntentionalElementRefs) )  {
					noOfsources++;
				}
			}
		}

		int [] sources=new int [noOfsources];
		int counterSources=0;
		for (int i=0; i<targets.length;i++){
			for (int j=0; j<allConnections.length;j++){
				if (allConnections[j][1]==targets[i] && containsElement(targets,allConnections[j][1]) && !chosen(allConnections[j][1], allElementsInAnActor, selectedElements) && isItInSameActor(allConnections[j][0], thisActor, listOfContRefsInActor,allIntentionalElementRefs)){
					sources[counterSources]=allConnections[j][0];
					counterSources++;
				}
			}
		}
		for (int i = 0; i < allElementsInAnActor.size(); i++){
			for(int j=0; j<targets.length;j++){
			if (allElementsInAnActor.get(i)== targets[j])
				selectedElements[i]=Boolean.TRUE;
			}
		}
		
		targets=new int [sources.length];
		
		for (int i=0; i<sources.length;i++){
			targets[i]=sources[i];
		}
	
		if(targets.length>0){
		depth++;
		}
	}
 
	//System.out.println("depth of this actor is: "+ depth);
	return depth;
		
}


float tacticalAdequacyCalculator(List<Integer> listOfGoalsAndSoftgoalsRefs,int [][] allConnections,List<Integer> listOfNodesInActor, String [][] allElements,int [][] nodes ){
	
List<Integer> listOfLowLevelGoalsAndSoftgoalsRefs = new ArrayList<Integer> ();
	
	for (int n=0;n<listOfGoalsAndSoftgoalsRefs.size(); n++) {
		listOfLowLevelGoalsAndSoftgoalsRefs.add(listOfGoalsAndSoftgoalsRefs.get(n)); 
		}
	
	List<Integer> listOfSources = new ArrayList<Integer> ();
	
	 for (int n=0;n<listOfGoalsAndSoftgoalsRefs.size(); n++) {
		 int target=listOfGoalsAndSoftgoalsRefs.get(n);
		 listOfSources.clear();
		 for (int m=0;m<allConnections.length; m++) {
			
			if (target == allConnections[m][1] && listOfNodesInActor.contains(allConnections[m][0]))
				listOfSources.add(allConnections[m][0]);
				}
		 

		 if (!listOfSources.isEmpty()) {
			 if (!isAllsourcesNotGoalsORSoftgoals(listOfSources,nodes,allElements) ) {
				 int indexTarget=listOfLowLevelGoalsAndSoftgoalsRefs.indexOf(target);	
				 listOfLowLevelGoalsAndSoftgoalsRefs.remove(indexTarget);
				 }
		 }
	 }
	 
	 List<Integer> listOfLowLevelGoalsAndSoftgoals= new ArrayList<Integer> ();
	 
	 for (int e=0;e<allElements.length;e++) {
		 if (!allElements[e][2].equalsIgnoreCase(""))
		 if(isAllRefsOfAnElementsLowLevels(allElements[e][2],listOfLowLevelGoalsAndSoftgoalsRefs))
			 listOfLowLevelGoalsAndSoftgoals.add(Integer.parseInt(allElements[e][0]));
	 }

	
	int noOfTasksAndResources =0;
	for (int i = 0; i < listOfNodesInActor.size(); i++){
		for (int j=0; j< allElements.length; j++){
			if (testingIfNodeIsReferedByRefs(listOfNodesInActor.get(i),allElements[j][2]) && ( allElements[j][1].equals("Ressource") || allElements[j][1].equals("Task")) )
				noOfTasksAndResources++;
		}
	}
	
	if(listOfLowLevelGoalsAndSoftgoals.size()==0)
		return 0;
	else return (100*((float) (noOfTasksAndResources))/((float) (listOfLowLevelGoalsAndSoftgoals.size()) ));
		
			
}


int actorCouplingCalculator(List <Integer> allElementsInAnActor,int [][] allConnections,int [] allDependencies){
	
	int actorCoupling=0;
	for (int i=0; i<allElementsInAnActor.size(); i++ )
		for (int j=0;j<allConnections.length;j++)
			if ((allElementsInAnActor.get(i) == allConnections[j][0] || allElementsInAnActor.get(i) == allConnections[j][1]) && containsLink(allConnections[j][2],allDependencies))
				actorCoupling++;
	
//	System.out.println("actorCoupling: "+actorCoupling);
	return actorCoupling;
		
}
		


}
			

