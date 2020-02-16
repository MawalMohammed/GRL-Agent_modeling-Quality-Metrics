package org.eclipse.jucmnav.grl.QAMM;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import seg.jUCMNav.editors.UCMNavMultiPageEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

/**
 * Copyright (C) 2020 Mawal Mohammed - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the Eclipse Public License - v 2.0 ,
 */

public class GRLMetricsCalculationEngine implements IEditorActionDelegate {

private IWorkbenchPage activePage;
private IEditorPart  activeEditor;
UCMNavMultiPageEditor jucmEditor;
private IFile activeFile;

String fileLoc;
Document jucmDoc;

NodeList actorList;

	@Override
	public void run(IAction action) {
		
		IPreferenceStore store =  org.eclipse.jucmnav.grl.QAMM.Activator.getDefault().getPreferenceStore();
		
		activePage = org.eclipse.jucmnav.grl.QAMM.Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
		activeEditor=activePage.getActiveEditor();
		jucmEditor = (UCMNavMultiPageEditor) activeEditor;
		
		jucmEditor.doSave(null);
	
		
		try {
			if(activeEditor  != null)
				{
				FileEditorInput input = (FileEditorInput) jucmEditor.getEditorInput() ;
				activeFile = input.getFile();
				
				fileLoc= activeFile.getRawLocation().makeAbsolute().toString();
				}
		
				activeFile.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			   } catch (Exception e) {
				   e.printStackTrace();
			   }
	
		
		// Start bad smells detection based on the preferences 
		try {
			
			File jucmFile = new File(fileLoc);
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			jucmDoc = docBuilder.parse(jucmFile);
			jucmDoc.getDocumentElement().normalize();
		}   catch (Exception e) {
			e.printStackTrace();
		}
		
		GRLMetricsCalculationPreprocessors MetricCalculationPreprocessor= new GRLMetricsCalculationPreprocessors(jucmEditor, activeFile, jucmDoc);
		
		// strategic Competency Metric
		if (store.getBoolean(GRLMetricsConstants.PRE_strategicCompetency)) {   
			MetricCalculationPreprocessor.strategicCompetencPreprocessor();
		}		

		//tactical Adequacy Metric
		if (store.getBoolean(GRLMetricsConstants.PRE_tacticalAdequacy)) { 
			MetricCalculationPreprocessor.tacticalAdequacyPreprocessor();	
		}
		
		//depth Of Refinement Metric
		if (store.getBoolean(GRLMetricsConstants.PRE_depthOfRefinement)) { 
			MetricCalculationPreprocessor.depthOfRefinementPreprocessor();	
			
		}
		
		//actor Coupling Metric
		if (store.getBoolean(GRLMetricsConstants.PRE_actorCoupling)) { 
			MetricCalculationPreprocessor.actorCouplingPreprocessor();
		}
		
		
	
		try {
		 
			activePage.showView("org.eclipse.ui.views.ProblemView");
		} catch (PartInitException e) {
		
			e.printStackTrace();
			}
		
		jucmEditor.setFocus();        
		      
	
	}
	
	    
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		// TODO Auto-generated method stub

	}

}
