package de.symeda.sormas.ui.reports.aggregate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import com.vaadin.ui.DateField;
import de.symeda.sormas.ui.utils.DateFormatHelper;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.data.fieldgroup.FieldGroup;
import com.vaadin.v7.ui.OptionGroup;

import de.symeda.sormas.api.Disease;
import de.symeda.sormas.api.FacadeProvider;
import de.symeda.sormas.api.infrastructure.facility.FacilityReferenceDto;
import de.symeda.sormas.api.i18n.Captions;
import de.symeda.sormas.api.i18n.I18nProperties;
import de.symeda.sormas.api.i18n.Strings;
import de.symeda.sormas.api.infrastructure.pointofentry.PointOfEntryReferenceDto;
import de.symeda.sormas.api.infrastructure.district.DistrictReferenceDto;
import de.symeda.sormas.api.infrastructure.region.RegionReferenceDto;
import de.symeda.sormas.api.report.AggregateReportCriteria;
import de.symeda.sormas.api.report.AggregateReportDto;
import de.symeda.sormas.api.utils.DateHelper;
import de.symeda.sormas.api.utils.EpiWeek;
import de.symeda.sormas.ui.UserProvider;
import de.symeda.sormas.ui.utils.ButtonHelper;
import de.symeda.sormas.ui.utils.CssStyles;
import de.symeda.sormas.ui.utils.EpiWeekFilterOption;
import de.symeda.sormas.ui.utils.VaadinUiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author Christopher Riedel
 * 
 *         Should better be an edit form and use an new DTO that contains the
 *         list of AggregateReport entries. The save method should be moved to a
 *         controller
 */
public class AggregateReportsEditLayout extends VerticalLayout {

	private static final long serialVersionUID = -7806379599024578146L;

	private static final DecimalFormat df = new DecimalFormat();

	private Window window;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private OptionGroup epiweekOptions;
	private ComboBox<Integer> comboBoxYear;
	private DateField dateField = new DateField();
	private ComboBox<EpiWeek> comboBoxEpiweek;
	private ComboBox<RegionReferenceDto> comboBoxRegion;
	private ComboBox<DistrictReferenceDto> comboBoxDistrict;
	private ComboBox<FacilityReferenceDto> comboBoxFacility;
	private ComboBox<PointOfEntryReferenceDto> comboBoxPoe;
	private Button cancelButton;
	private Button saveButton;
	private List<AggregateReportEditForm> editForms = new ArrayList<>();
	private Map<String, Disease> diseaseMap;
	private Map<String, Disease> diseasesWithoutReport;
	private Map<Disease, AggregateReportDto> reports;
	private boolean popUpIsShown = false;

	public AggregateReportsEditLayout(Window window, AggregateReportCriteria criteria, boolean edit, boolean fetchdisease) {

		setWidth(560, Unit.PIXELS);
		setSpacing(false);

		this.window = window;

		epiweekOptions = new OptionGroup();
		epiweekOptions.addItems(EpiWeekFilterOption.values());
		epiweekOptions.addStyleNames(ValoTheme.OPTIONGROUP_HORIZONTAL, CssStyles.OPTIONGROUP_HORIZONTAL_PRIMARY);
		CssStyles.style(epiweekOptions, ValoTheme.OPTIONGROUP_HORIZONTAL);
		epiweekOptions.addValueChangeListener(e -> updateEpiweekFields());
		// if (!edit) {
		// 	addComponent(epiweekOptions);
		// }

		comboBoxYear = new ComboBox<>(I18nProperties.getString(Strings.year), DateHelper.getYearsToNow(2000));
		comboBoxYear.setWidth(250, Unit.PIXELS);
		dateField.setWidth(250, Unit.PIXELS);
		dateField.setCaption("Date");
		dateField.setDateFormat(DateFormatHelper.getDateFormatPattern());
		comboBoxYear.addValueChangeListener(e -> updateEpiweekFields());

		comboBoxEpiweek = new ComboBox<>(I18nProperties.getString(Strings.epiWeek));
		comboBoxEpiweek.setWidth(250, Unit.PIXELS);
		if (!edit) {
			comboBoxEpiweek.addValueChangeListener(e -> checkForExistingData(fetchdisease));
		}
		addComponent(new HorizontalLayout(dateField));

		comboBoxRegion = new ComboBox<>();
		comboBoxRegion.setWidth(250, Unit.PIXELS);
		comboBoxRegion.setCaption(I18nProperties.getPrefixCaption(AggregateReportDto.I18N_PREFIX, AggregateReportDto.REGION));
		comboBoxRegion.setItems(FacadeProvider.getRegionFacade().getAllActiveByServerCountry());
		comboBoxRegion.addValueChangeListener(e -> {
			RegionReferenceDto region = e.getValue();
			comboBoxDistrict.clear();
			if (region != null) {
				comboBoxDistrict.setItems(FacadeProvider.getDistrictFacade().getAllActiveByRegion(region.getUuid()));
				comboBoxDistrict.setEnabled(true);
			} else {
				comboBoxDistrict.setEnabled(false);
			}
		});

		comboBoxDistrict = new ComboBox<>();
		comboBoxDistrict.setWidth(250, Unit.PIXELS);
		comboBoxDistrict.setCaption(I18nProperties.getPrefixCaption(AggregateReportDto.I18N_PREFIX, AggregateReportDto.DISTRICT));
		comboBoxDistrict.addValueChangeListener(e -> {
			DistrictReferenceDto district = e.getValue();
			if (comboBoxFacility != null) {
				comboBoxFacility.clear();
			}
			if (comboBoxPoe != null) {
				comboBoxPoe.clear();
			}
			if (district != null) {
				if (comboBoxFacility != null) {
					comboBoxFacility.setItems(FacadeProvider.getFacilityFacade().getActiveHospitalsByDistrict(district, false));
					comboBoxFacility.setEnabled(true);
				}
				if (comboBoxPoe != null) {
					comboBoxPoe.setItems(FacadeProvider.getPointOfEntryFacade().getAllActiveByDistrict(district.getUuid(), false));
					comboBoxPoe.setEnabled(true);
				}
			} else {
				comboBoxFacility.setEnabled(false);
				comboBoxPoe.setEnabled(false);
			}
		});

		comboBoxDistrict.setEnabled(false);
		addComponent(new HorizontalLayout(comboBoxRegion, comboBoxDistrict));

		comboBoxFacility = new ComboBox<>();
		comboBoxFacility.setWidth(250, Unit.PIXELS);
		comboBoxFacility.setCaption(I18nProperties.getPrefixCaption(AggregateReportDto.I18N_PREFIX, AggregateReportDto.HEALTH_FACILITY));
		comboBoxFacility.setEnabled(false);
		comboBoxFacility.addValueChangeListener(e -> {
			if (comboBoxFacility.getValue() != null) {
				comboBoxPoe.clear();
			}
			if (!edit) {
				checkForExistingData(fetchdisease);
			}
		});

		comboBoxPoe = new ComboBox<>();
		comboBoxPoe.setWidth(250, Unit.PIXELS);
		comboBoxPoe.setCaption(I18nProperties.getPrefixCaption(AggregateReportDto.I18N_PREFIX, AggregateReportDto.POINT_OF_ENTRY));
		comboBoxPoe.setEnabled(false);
		comboBoxPoe.addValueChangeListener(e -> {
			// if (comboBoxPoe.getValue() != null) {
			// 	comboBoxFacility.clear();
			// }
			if (!edit) {
				checkForExistingData(fetchdisease);
			}
		});
		addComponent(new HorizontalLayout(comboBoxFacility, comboBoxPoe));

		if (edit) {
			comboBoxYear.setValue(criteria.getEpiWeekFrom().getYear());
			comboBoxYear.setEnabled(false);
			comboBoxEpiweek.setValue(criteria.getEpiWeekFrom());
			comboBoxEpiweek.setEnabled(false);
			comboBoxRegion.setValue(criteria.getRegion());
			comboBoxRegion.setEnabled(false);
			comboBoxDistrict.setValue(criteria.getDistrict());
			comboBoxDistrict.setEnabled(false);
			comboBoxFacility.setValue(criteria.getHealthFacility());
			comboBoxFacility.setEnabled(false);
			comboBoxPoe.setValue(criteria.getPointOfEntry());
			comboBoxPoe.setEnabled(false);
			reports = FacadeProvider.getAggregateReportFacade()
				.getList(criteria)
				.stream()
				.collect(Collectors.toMap(AggregateReportDto::getDisease, dto -> dto));
		}

		List<Disease> diseaseList = FacadeProvider.getDiseaseConfigurationFacade().getAllDiseases(true, null, false);
		diseaseMap = diseaseList.stream().collect(Collectors.toMap(Disease::toString, disease -> disease));
		diseasesWithoutReport = new HashMap<String, Disease>(diseaseMap);
		if (reports != null) {
			for (AggregateReportDto report : reports.values()) {
				String disease = report.getDisease().toString();
				if (fetchdisease == true) {
					if (!disease.startsWith("Proportion")
					&& !disease.startsWith("Nombre")
					&& !disease.startsWith("Number")){
						AggregateReportEditForm editForm = new AggregateReportEditForm(disease, fetchdisease, true);
						editForm.setNewCases(report.getNewCases());
						editForm.setLabConfirmations(report.getLabConfirmations());
						editForm.setDeaths(report.getDeaths());
						editForms.add(editForm);
						diseasesWithoutReport.remove(disease);
					}
				}
				if (fetchdisease == false) {
					boolean enable = true;
					if (disease.startsWith("Proportion")
					|| disease.startsWith("Nombre")
					|| disease.startsWith("Number")) {
						if (disease.startsWith("Number") || disease.startsWith("Nombre")) {
							enable = false;
						}
						AggregateReportEditForm editForm = new AggregateReportEditForm(disease, fetchdisease, enable);
						editForm.setNewCases(0);
						editForm.setLabConfirmations(0);
						editForm.setDeaths(0);
						editForms.add(editForm);
						diseasesWithoutReport.remove(disease);
					}
				}
			}
		}

		for (String disease : diseasesWithoutReport.keySet()) {
			if (fetchdisease == true) {
				if (!disease.startsWith("Proportion")
				&& !disease.startsWith("Nombre")
				&& !disease.startsWith("Number")) {
					AggregateReportEditForm editForm = new AggregateReportEditForm(disease, fetchdisease, true);
					editForms.add(editForm);
				}
			}
			if (fetchdisease == false) {
				boolean enable = true;
				if (disease.startsWith("Proportion")
				|| disease.startsWith("Nombre")
				|| disease.startsWith("Number")){
					if (disease.startsWith("Number") || disease.startsWith("Nombre")) {
						enable = false;
					}
					AggregateReportEditForm editForm = new AggregateReportEditForm(disease, fetchdisease, enable);
					editForms.add(editForm);
				}
			}
		}

		Label legend = new Label(
			String.format(
				I18nProperties.getString(Strings.aggregateReportLegend),
				I18nProperties.getCaption(Captions.aggregateReportNewCasesShort),
				I18nProperties.getPrefixCaption(AggregateReportDto.I18N_PREFIX, AggregateReportDto.NEW_CASES),
				I18nProperties.getCaption(Captions.aggregateReportLabConfirmationsShort),
				I18nProperties.getPrefixCaption(AggregateReportDto.I18N_PREFIX, AggregateReportDto.LAB_CONFIRMATIONS),
				I18nProperties.getCaption(Captions.aggregateReportDeathsShort),
				I18nProperties.getPrefixCaption(AggregateReportDto.I18N_PREFIX, AggregateReportDto.DEATHS)));
		if (fetchdisease == true) {
			addComponent(legend);
			legend.addStyleName(CssStyles.VSPACE_TOP_1);
		}

		editForms.sort((e1, e2) -> e1.getDisease().compareTo(e2.getDisease()));
		for (AggregateReportEditForm editForm : editForms) {
			addComponent(editForm);
		}

		if (!editForms.isEmpty()) {
			editForms.get(0).addStyleName(CssStyles.VSPACE_TOP_1);
		}

		HorizontalLayout buttonsPanel = new HorizontalLayout();
		buttonsPanel.setMargin(false);
		buttonsPanel.setSpacing(true);
		buttonsPanel.setWidth(100, Unit.PERCENTAGE);

		cancelButton = ButtonHelper.createButton(Captions.actionDiscard, e -> window.close());

		buttonsPanel.addComponent(cancelButton);
		buttonsPanel.setComponentAlignment(cancelButton, Alignment.BOTTOM_RIGHT);
		buttonsPanel.setExpandRatio(cancelButton, 1);

		saveButton = ButtonHelper.createButton(Captions.actionSave, event -> save(fetchdisease), ValoTheme.BUTTON_PRIMARY);

		buttonsPanel.addComponent(saveButton);
		buttonsPanel.setComponentAlignment(saveButton, Alignment.BOTTOM_RIGHT);
		buttonsPanel.setExpandRatio(saveButton, 0);

		buttonsPanel.addStyleName(CssStyles.VSPACE_TOP_2);
		addComponent(buttonsPanel);
		setComponentAlignment(buttonsPanel, Alignment.BOTTOM_RIGHT);

		if (!edit) {
			initialize();
		}
	}

	private void checkForExistingData(boolean fetchdisease) {
		if (dateField.getValue() != null && (comboBoxFacility.getValue() != null || comboBoxPoe.getValue() != null) && !popUpIsShown) {
			AggregateReportCriteria criteria = new AggregateReportCriteria();
			criteria.setDistrict(comboBoxDistrict.getValue());
			SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
			Date fdate = new Date();
			try {
				fdate = format.parse(dateField.getValue().toString());
			} catch (ParseException e) {
				logger.info("Erreur lors du parsing de la seconde date {}", e);
			}
			criteria.setReportingDateFrom(fdate);
			criteria.setReportingDateTo(fdate);
			criteria.setEpiWeekFrom(comboBoxEpiweek.getValue());
			criteria.setEpiWeekTo(comboBoxEpiweek.getValue());
			criteria.setHealthFacility(comboBoxFacility.getValue());
			criteria.setPointOfEntry(comboBoxPoe.getValue());
			criteria.setRegion(comboBoxRegion.getValue());
			reports = FacadeProvider.getAggregateReportFacade()
				.getList(criteria)
				.stream()
				.collect(Collectors.toMap(AggregateReportDto::getDisease, dto -> dto));
			if (!reports.isEmpty()) {
				popUpIsShown = true;
				Consumer<Boolean> resultConsumer = new Consumer<Boolean>() {

					@Override
					public void accept(Boolean edit) {
						if (edit) {
							switchToEditMode(fetchdisease);
						} else {
							;
							// comboBoxFacility.clear();
							// comboBoxPoe.clear();
						}
						popUpIsShown = false;
					}
				};
				VaadinUiUtil.showChooseOptionPopup(
					I18nProperties.getCaption(Captions.aggregateReportReportFound),
					new Label(I18nProperties.getString(Strings.messageAggregateReportFound)),
					I18nProperties.getCaption(Captions.aggregateReportEditReport),
					I18nProperties.getCaption(Captions.aggregateReportDiscardSelection),
					new Integer(480),
					resultConsumer);
			}
		}
	}

	private void initialize() {

		epiweekOptions.setValue(EpiWeekFilterOption.THIS_WEEK);
	}

	private void switchToEditMode(boolean fetchdisease) {

		window.setCaption(I18nProperties.getString(Strings.headingEditAggregateReport));
		for (AggregateReportEditForm editForm : editForms) {
			String disease = editForm.getDisease();
			AggregateReportDto report = reports.get(diseaseMap.get(disease));
			if (report != null) {
				if (fetchdisease == true) {
					editForm.setNewCases(report.getNewCases());
					editForm.setLabConfirmations(report.getLabConfirmations());
					editForm.setDeaths(report.getDeaths());
					editForm.setNumerator(0);
					editForm.setDenominator(1);
					editForm.setProportion(0);
				}
				if (fetchdisease == false) {
					if (editForm.getDisease().startsWith("Proportion")
					|| editForm.getDisease().startsWith("Nombre")
					|| editForm.getDisease().startsWith("Number")) {
						if (editForm.getDisease().startsWith("Number") || editForm.getDisease().startsWith("Nombre")) {
							editForm.setNumerator(report.getNumerator());
							editForm.setDenominator(1);
							editForm.setProportion(0);
						} else {
							editForm.setNumerator(report.getNumerator());
							editForm.setDenominator(report.getDenominator());
							editForm.setProportion(report.getProportion());
						}
					}
				}
				diseasesWithoutReport.remove(disease);
			}
		}
		removeComponent(epiweekOptions);
		comboBoxYear.setEnabled(false);
		comboBoxEpiweek.setEnabled(false);
		comboBoxRegion.setEnabled(false);
		comboBoxDistrict.setEnabled(false);
		comboBoxFacility.setEnabled(false);
		comboBoxPoe.setEnabled(false);
	}

	private void save(boolean fetchdisease) {
		if (!isValid()) {
			Notification.show(I18nProperties.getString(Strings.errorIntegerFieldValidationFailed), "", Type.ERROR_MESSAGE);
			return;
		}

		if (!isValidDenominator()) {
			Notification.show(I18nProperties.getString(Strings.errorDenomFieldValidationFailed), "", Type.ERROR_MESSAGE);
			return;
		}

		if (!isValidNumerator()) {
			Notification.show(I18nProperties.getString(Strings.errorNumerFieldValidationFailed), "", Type.ERROR_MESSAGE);
			return;
		}

		for (AggregateReportEditForm editForm : editForms) {

			int deaths = 0;
			int labConfirmations = 0;
			int newCases = 0;
			int numerator = 0;
			int denominator = 0;
			double proportion = 0;
			if (fetchdisease == true) {
				String deathsFieldValue = (String) editForm.getField(AggregateReportDto.DEATHS).getValue();
				String labFieldValue = (String) editForm.getField(AggregateReportDto.LAB_CONFIRMATIONS).getValue();
				String caseFieldValue = (String) editForm.getField(AggregateReportDto.NEW_CASES).getValue();
				if (!deathsFieldValue.isEmpty()) {
					deaths = Integer.parseInt(deathsFieldValue);
				}

				if (!labFieldValue.isEmpty()) {
					labConfirmations = Integer.parseInt(labFieldValue);
				}

				if (!caseFieldValue.isEmpty()) {
					newCases = Integer.parseInt(caseFieldValue);
				}
			}
			if (fetchdisease == false) {
				String numeratorFieldValue = (String) editForm.getField(AggregateReportDto.NUMERATOR).getValue();
				String denominatorFieldValue = (String) editForm.getField(AggregateReportDto.DENOMINATOR).getValue();
				String proportionFieldValue = (String) editForm.getField(AggregateReportDto.PROPORTION).getValue();
				if (!numeratorFieldValue.isEmpty()) {
					numerator = Integer.parseInt(numeratorFieldValue);
				}
	
				if (!denominatorFieldValue.isEmpty()) {
					denominator = Integer.parseInt(denominatorFieldValue);
				}
	
				if (denominator > 0 && numerator >= 0){
					int n = numerator;
					int d = denominator;
					df.setMaximumFractionDigits(2);
					double prop = Double.valueOf(df.format((float) n / (float) d));
					proportion = prop;
				}
			}

			AggregateReportDto report = null;
			if (reports != null) {
				report = reports.get(diseaseMap.get(editForm.getDisease()));
			}
			if (report != null && (deaths > 0 || labConfirmations > 0 || newCases > 0 || numerator > 0 || denominator > 0)) {
				report.setDeaths(deaths);
				report.setLabConfirmations(labConfirmations);
				report.setNewCases(newCases);
				report.setNumerator(numerator);
				report.setDenominator(denominator);
				report.setProportion(proportion);
				FacadeProvider.getAggregateReportFacade().saveAggregateReport(report);
			} else if (report != null) {
				FacadeProvider.getAggregateReportFacade().deleteReport(report.getUuid());
			} else {
				if (deaths > 0 || labConfirmations > 0 || newCases > 0 || denominator > 0 || numerator > 0) {
					AggregateReportDto newReport = AggregateReportDto.build();
					newReport.setDeaths(deaths);
					newReport.setDisease(diseaseMap.get(editForm.getDisease()));
					newReport.setDistrict(comboBoxDistrict.getValue());
					newReport.setEpiWeek(comboBoxEpiweek.getValue().getWeek());
					SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
					Date finalDate = new Date();
					try {
						finalDate = format.parse(dateField.getValue().toString());
					} catch (ParseException e) {
						logger.info("Erreur lors du parsing de la date {}", e);
					}
					newReport.setReportingdate(finalDate);
					newReport.setHealthFacility(comboBoxFacility.getValue());
					newReport.setLabConfirmations(labConfirmations);
					newReport.setNewCases(newCases);
					newReport.setPointOfEntry(comboBoxPoe.getValue());
					newReport.setRegion(comboBoxRegion.getValue());
					newReport.setReportingUser(UserProvider.getCurrent().getUser().toReference());
					newReport.setYear(comboBoxYear.getValue());
					newReport.setNumerator(numerator);
					newReport.setDenominator(denominator);
					newReport.setProportion(proportion);

					FacadeProvider.getAggregateReportFacade().saveAggregateReport(newReport);
				}
			}
		}

		window.close();
	}

	private boolean isValid() {
		boolean valid = true;
		for (AggregateReportEditForm editForm : editForms) {
			if (!editForm.isValid()) {
				valid = false;
			}
		}
		return valid;
	}

	private boolean isValidDenominator() {
		boolean isvalid = true;
		for (AggregateReportEditForm editForm : editForms) {
			if (!editForm.isValidDenominator()) {
				isvalid = false;
			}
		}
		return isvalid;
	}

	private boolean isValidNumerator() {
		boolean result = true;
		for (AggregateReportEditForm editForm : editForms) {
			if (!editForm.isValidNumerator()) {
				result = false;
			}
		}
		return result;
	}

	private void updateEpiweekFields() {

		boolean enableEpiweekFields;

		if (EpiWeekFilterOption.LAST_WEEK.equals(epiweekOptions.getValue())) {

			EpiWeek lastEpiweek = DateHelper.getPreviousEpiWeek(Calendar.getInstance().getTime());

			comboBoxYear.setValue(lastEpiweek.getYear());
			comboBoxEpiweek.setValue(lastEpiweek);

			enableEpiweekFields = false;

		} else if (EpiWeekFilterOption.THIS_WEEK.equals(epiweekOptions.getValue())) {

			EpiWeek thisEpiweek = DateHelper.getEpiWeek(Calendar.getInstance().getTime());

			comboBoxYear.setValue(thisEpiweek.getYear());
			comboBoxEpiweek.setValue(thisEpiweek);

			enableEpiweekFields = false;

		} else {
			enableEpiweekFields = true;
		}

		comboBoxYear.setEnabled(enableEpiweekFields);
		comboBoxEpiweek.setEnabled(enableEpiweekFields);

		Integer year = comboBoxYear.getValue();

		if (year != null) {
			comboBoxEpiweek.setItems(DateHelper.createEpiWeekList(year));
		} else {
			comboBoxEpiweek.clear();
		}
	}

	public FieldGroup getFieldGroups() {
		// TODO Auto-generated method stub
		return null;
	}
}
