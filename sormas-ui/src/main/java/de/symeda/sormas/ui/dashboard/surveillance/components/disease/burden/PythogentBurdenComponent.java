package de.symeda.sormas.ui.dashboard.surveillance.components.disease.burden;

import java.util.List;
import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.v7.shared.ui.grid.HeightMode;

import de.symeda.sormas.api.disease.DiseaseBurdenDto;
import de.symeda.sormas.api.sample.PathogenTestDto;
import de.symeda.sormas.api.sample.PathogenTestType;
import de.symeda.sormas.api.sample.SampleDto;
import de.symeda.sormas.api.FacadeProvider;
import de.symeda.sormas.api.i18n.Captions;
import de.symeda.sormas.api.i18n.I18nProperties;
import de.symeda.sormas.ui.utils.CssStyles;

public class PythogentBurdenComponent extends VerticalLayout{
    private static final long serialVersionUID = 6582975657305031105L;
	List<PathogenTestType> TestTypes = new ArrayList<PathogenTestType>();
	public PythogentBurdenComponent() {
	HorizontalLayout formDataLayout = new HorizontalLayout();
	{
		String PersonList = Integer.toString(FacadeProvider.getPersonFacade().getAllUuids().size());
		Label titles = new Label(I18nProperties.getCaption(Captions.dashboardNumberOfPerson));
		CssStyles.style(titles, CssStyles.H2, CssStyles.VSPACE_4, CssStyles.VSPACE_TOP_NONE);
		TextField text = new TextField();
		text.setValue(PersonList);
		text.setReadOnly(true);
		formDataLayout.addComponent(titles);
		formDataLayout.addComponent(text);
	}	
	for(PathogenTestType ptt : PathogenTestType.values()){
		TestTypes.add(ptt);
	}
	List<String> newList = TestTypes.stream()
			.map(String::valueOf)
			.collect(Collectors.toList());
	int count;
	List<String> TestType = FacadeProvider.getSampleFacade().getTypeTest();
	List<String> lists=new ArrayList<String>(); 
		for(String requested : TestType){
			if(requested != null){
				List<String> lis = new ArrayList<String>(Arrays.asList(requested.split( "[\\s,]+")));
				for(String ts : lis){
					lists.add(ts);
				}
			}
		
		}
	
	List<Integer> occurrences = new ArrayList<Integer>();
	int occurrences_1 = Collections.frequency(lists, "ANTIBODY_DETECTION");
	occurrences.add(occurrences_1);
	int occurrences_2 = Collections.frequency(lists, "ANTIGEN_DETECTION");
	occurrences.add(occurrences_2);
	int occurrences_3 = Collections.frequency(lists, "RAPID_TEST");
	occurrences.add(occurrences_3);
	int occurrences_4 = Collections.frequency(lists, "CULTURE");
	occurrences.add(occurrences_4);
	int occurrences_5 = Collections.frequency(lists, "HISTOPATHOLOGY");
	occurrences.add(occurrences_5);
	int occurrences_6 = Collections.frequency(lists, "ISOLATION");
	occurrences.add(occurrences_6);
	int occurrences_7 = Collections.frequency(lists, "IGM_SERUM_ANTIBODY");
	occurrences.add(occurrences_7);
	int occurrences_8 = Collections.frequency(lists, "IGG_SERUM_ANTIBODY");
	occurrences.add(occurrences_8);
	int occurrences_9 = Collections.frequency(lists, "IGA_SERUM_ANTIBODY");
	occurrences.add(occurrences_9);
	int occurrences_10 = Collections.frequency(lists, "INCUBATION_TIME");
	occurrences.add(occurrences_10);
	int occurrences_11 = Collections.frequency(lists, "INDIRECT_FLUORESCENT_ANTIBODY");
	occurrences.add(occurrences_11);
	int occurrences_12 = Collections.frequency(lists, "DIRECT_FLUORESCENT_ANTIBODY");
	occurrences.add(occurrences_12);
	int occurrences_13 = Collections.frequency(lists, "MICROSCOPY");
	occurrences.add(occurrences_13);
	int occurrences_14 = Collections.frequency(lists, "NEUTRALIZING_ANTIBODIES");
	occurrences.add(occurrences_14);
	int occurrences_15 = Collections.frequency(lists, "PCR_RT_PCR");
	occurrences.add(occurrences_15);
	int occurrences_16 = Collections.frequency(lists, "GRAM_STAIN");
	occurrences.add(occurrences_16);
	int occurrences_17 = Collections.frequency(lists, "LATEX_AGGLUTINATION");
	occurrences.add(occurrences_17);
	int occurrences_18 = Collections.frequency(lists, "CQ_VALUE_DETECTION");
	occurrences.add(occurrences_18);
	int occurrences_19 = Collections.frequency(lists, "SEQUENCING");
	occurrences.add(occurrences_19);
	int occurrences_20 = Collections.frequency(lists, "DNA_MICROARRAY");
	occurrences.add(occurrences_20);
	int occurrences_21 = Collections.frequency(lists, "D_DIMERES");
	occurrences.add(occurrences_21);
	int occurrences_22 = Collections.frequency(lists, "TP");
	occurrences.add(occurrences_22);
	int occurrences_23 = Collections.frequency(lists, "TCK");
	occurrences.add(occurrences_23);
	int occurrences_24 = Collections.frequency(lists, "OTHER");
	occurrences.add(occurrences_24);
	List<String> newOccurence = occurrences.stream()
			.map(String::valueOf)
			.collect(Collectors.toList());		
	String dashboardPathogenBurdenInfo = "dashboardPathogenBurdenInfo";
		Label title = new Label(I18nProperties.getCaption(Captions.dashboardPathogenBurdenInfo));
		CssStyles.style(title, CssStyles.H2, CssStyles.VSPACE_4, CssStyles.VSPACE_TOP_NONE);

		// layout
		setWidth(100, Unit.PERCENTAGE);

		addComponent(formDataLayout);
		addComponent(title);
		for(String ptt : newList){
			if(ptt == newList.get(0)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(0));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(1)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(1));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(2)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(2));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(3)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(3));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(4)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(4));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(5)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(5));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(6)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(6));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(7)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(7));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(8)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(8));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(9)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(9));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(10)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(10));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(11)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(11));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(12)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(12));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(13)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(13));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(14)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(14));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(15)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(15));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(16)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(16));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(17)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(17));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(18)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(18));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(19)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(19));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(20)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(20));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(21)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(21));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(22)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(22));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}
			else if(ptt == newList.get(23)){
				TextField textField = new TextField(ptt);
				textField.setValue(newOccurence.get(23));
				textField.setWidth("55%");
				textField.setReadOnly(true);
				addComponent(textField);
			}

		}
		setMargin(new MarginInfo(true, true, false, true));
		setSpacing(false);
	}

}
