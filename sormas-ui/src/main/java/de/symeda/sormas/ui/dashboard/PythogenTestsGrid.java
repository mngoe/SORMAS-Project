package de.symeda.sormas.ui.dashboard;

import de.symeda.sormas.api.i18n.I18nProperties;
import de.symeda.sormas.api.sample.PathogenTestType;
import de.symeda.sormas.ui.utils.CssStyles;
import de.symeda.sormas.api.i18n.Strings;
import de.symeda.sormas.api.FacadeProvider;
import de.symeda.sormas.api.i18n.Captions;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.vaadin.v7.ui.Grid;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class PythogenTestsGrid extends Grid {

	public PythogenTestsGrid() {
		List<PathogenTestType> TestTypes = new ArrayList<PathogenTestType>();
		String PersonList = Integer.toString(FacadeProvider.getPersonFacade().getAllUuids().size());
		String NbOfpessengers = new Label(I18nProperties.getCaption(Captions.dashboardNumberOfPerson)).getValue();
		String title1 = new Label(I18nProperties.getString(Strings.labellibelle)).getValue();
		String title2 = new Label(I18nProperties.getString(Strings.labelvalue)).getValue();

		for(PathogenTestType ptt : PathogenTestType.values()){
			TestTypes.add(ptt);
		}
		List<String> newList = TestTypes.stream()
				.map(String::valueOf)
				.collect(Collectors.toList());
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
			Label title = new Label(I18nProperties.getCaption(Captions.dashboardPathogenBurdenInfo));
			CssStyles.style(title, CssStyles.H2, CssStyles.VSPACE_4, CssStyles.VSPACE_TOP_NONE);

		setSizeFull();

		setColumns(
			title1,
			title2
			);
		addRow(
			new Object[] {
				NbOfpessengers, PersonList });
		int i = 0;
		for(String ptt : newList){
			addRow(
			new Object[] {
				ptt, newOccurence.get(i).toString() });
				i++;
		}
		setSelectionMode(SelectionMode.NONE);
	}
}
