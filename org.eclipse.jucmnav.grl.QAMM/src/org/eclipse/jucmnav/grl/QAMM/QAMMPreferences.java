package org.eclipse.jucmnav.grl.QAMM;


import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;


/**
 * Copyright (C) 2020 Mawal Mohammed - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the Eclipse Public License - v 2.0 ,
 */

public class QAMMPreferences extends PreferencePage implements
        IWorkbenchPreferencePage, SelectionListener, ModifyListener {
   
	
	private Button  strategicCompetency;
	private Button  tacticalAdequacy;
	private Button  depthOfRefinement;
    private Button  actorCoupling;
    

    /**
     * Creates an new checkbox instance and sets the default
     * layout data.
     *
     * @param group  the composite in which to create the checkbox
     * @param label  the string to set into the checkbox
     * @return the new checkbox
     */
    private Button createCheckBox(Composite group, String label) {
        Button button = new Button(group, SWT.CHECK | SWT.LEFT);
        button.setText(label);
        button.addSelectionListener(this);
        GridData data = new GridData();
        button.setLayoutData(data);
        return button;
    }

    /**
     * Creates composite control and sets the default layout data.
     *
     * @param parent  the parent of the new composite
     * @param numColumns  the number of columns for the new composite
     * @return the newly-created coposite
     */
    private Composite createComposite(Composite parent, int numColumns) {
        Composite composite = new Composite(parent, SWT.NULL);

        //GridLayout
        GridLayout layout = new GridLayout();
        layout.numColumns = numColumns;
        composite.setLayout(layout);

        //GridData
        GridData data = new GridData();
        data.verticalAlignment = GridData.FILL;
        data.horizontalAlignment = GridData.FILL;
        composite.setLayoutData(data);
        return composite;
    }

    /** (non-Javadoc)
     * Method declared on PreferencePage
     */
    protected Control createContents(Composite parent) {
    
        //composite_tab2 << parent
        Composite composite_tab2 = createComposite(parent, 1);
        createLabel(composite_tab2, "Metrics: "); //$NON-NLS-1$
        
        //
        tabForward(composite_tab2);
        //composite_checkBox << composite_tab2
        Composite composite_checkBox = createComposite(parent, 1);
        
     
        strategicCompetency=createCheckBox(composite_checkBox,"Strategic Competency");
        
        
        tacticalAdequacy=createCheckBox(composite_checkBox,"Tactical Adequacy");
   	

        depthOfRefinement=createCheckBox(composite_checkBox,"Depth Of Refinement");
    
       
        actorCoupling=createCheckBox(composite_checkBox,"Actor Coupling");
        
               
       initializeValues();

        //font = null;
        return new Composite(parent, SWT.NULL);
    }

   
    private Label createLabel(Composite parent, String text) {
        Label label = new Label(parent, SWT.LEFT);
        label.setText(text);
        GridData data = new GridData();
        data.horizontalSpan = 1;
        data.horizontalAlignment = GridData.FILL;
        label.setLayoutData(data);
        return label;
    }

    

  
    protected IPreferenceStore doGetPreferenceStore() {
        return org.eclipse.jucmnav.grl.QAMM.Activator.getDefault().getPreferenceStore();
    }

 
    public void init(IWorkbench workbench) {
        // do nothing
    }

    /**
     * Initializes states of the controls using default values
     * in the preference store.
     */
    private void initializeDefaults() {
     
        
    	strategicCompetency.setSelection(GRLMetricsConstants.Default_strategicCompetency);
    	tacticalAdequacy.setSelection(GRLMetricsConstants.Default_tacticalAdequacy);
    	depthOfRefinement.setSelection(GRLMetricsConstants.Default_depthOfRefinement);
    	actorCoupling.setSelection(GRLMetricsConstants.Default_actorCoupling);
               
      }

    /**
     * Initializes states of the controls from the preference store.
     */
    private void initializeValues() {
    	
    	IPreferenceStore store =  org.eclipse.jucmnav.grl.QAMM.Activator.getDefault().getPreferenceStore();
    	
    	strategicCompetency.setSelection(store.getBoolean(GRLMetricsConstants.PRE_strategicCompetency));
    	tacticalAdequacy.setSelection(store.getBoolean(GRLMetricsConstants.PRE_tacticalAdequacy));
    	depthOfRefinement.setSelection(store.getBoolean(GRLMetricsConstants.PRE_depthOfRefinement));
    	actorCoupling.setSelection(store.getBoolean(GRLMetricsConstants.PRE_actorCoupling));
        
        
       // textField.setText(store.getString(GRLMetricsConstants.PRE_TEXT));
    }

    /** (non-Javadoc)
     * Method declared on ModifyListener
     */
    public void modifyText(ModifyEvent event) {
        //Do nothing on a modification in this example
    }

    /* (non-Javadoc)
     * Method declared on PreferencePage
     */
    protected void performDefaults() {
        super.performDefaults();
        initializeDefaults();
    }

    /* (non-Javadoc)
     * Method declared on PreferencePage
     */
    public boolean performOk() {
        storeValues();
        org.eclipse.jucmnav.grl.QAMM.Activator.getDefault().savePluginPreferences();
        return true;
    }

    /**
     * Stores the values of the controls back to the preference store.
     */
    private void storeValues() {
    	
        IPreferenceStore store = getPreferenceStore();
        
        store.setValue(GRLMetricsConstants.PRE_strategicCompetency, strategicCompetency.getSelection());
        store.setValue(GRLMetricsConstants.PRE_tacticalAdequacy, tacticalAdequacy.getSelection());
        store.setValue(GRLMetricsConstants.PRE_depthOfRefinement, depthOfRefinement.getSelection());
        store.setValue(GRLMetricsConstants.PRE_actorCoupling, actorCoupling.getSelection());
       
       
      //  store.setValue(GRLMetricsConstants.PRE_TEXT, textField.getText());
    }

    /**
     * Creates a tab of one horizontal spans.
     *
     * @param parent  the parent in which the tab should be created
     */
    private void tabForward(Composite parent) {
        Label vfiller = new Label(parent, SWT.LEFT);
        GridData gridData = new GridData();
        gridData = new GridData();
        gridData.horizontalAlignment = GridData.BEGINNING;
        gridData.grabExcessHorizontalSpace = false;
        gridData.verticalAlignment = GridData.CENTER;
        gridData.grabExcessVerticalSpace = false;
        vfiller.setLayoutData(gridData);
    }

    /** (non-Javadoc)
     * Method declared on SelectionListener
     */
    public void widgetDefaultSelected(SelectionEvent event) {
        //Handle a default selection. Do nothing in this example
    }

    /** (non-Javadoc)
     * Method declared on SelectionListener
     */
    public void widgetSelected(SelectionEvent event) {
        //Do nothing on selection in this example;
    }
}