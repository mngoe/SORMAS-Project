package de.symeda.sormas.ui.reports.aggregate;

import com.vaadin.v7.ui.Label;
import com.vaadin.v7.ui.TextField;

import de.symeda.sormas.api.i18n.Captions;
import de.symeda.sormas.api.i18n.I18nProperties;
import de.symeda.sormas.api.i18n.Validations;
import de.symeda.sormas.api.report.AggregateReportDto;
import de.symeda.sormas.api.utils.DataHelper;
import de.symeda.sormas.ui.utils.AbstractEditForm;
import de.symeda.sormas.ui.utils.CssStyles;
import de.symeda.sormas.ui.utils.LayoutUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Christopher Riedel
 */
public class AggregateReportEditForm extends AbstractEditForm<AggregateReportDto> {

	private static final long serialVersionUID = 2224137772717110789L;

	static final String DISEASE_LOC = "diseaseLoc";

	private String disease;

	private boolean fetchdisease;

	private boolean enable;

	private boolean initialized = false;

	private static final String HTML_LAYOUT = LayoutUtil.fluidRow(
		LayoutUtil.oneOfTwoCol(DISEASE_LOC),
		LayoutUtil.oneOfSixCol(AggregateReportDto.NEW_CASES),
		LayoutUtil.oneOfSixCol(AggregateReportDto.LAB_CONFIRMATIONS),
		LayoutUtil.oneOfSixCol(AggregateReportDto.DEATHS),
		LayoutUtil.oneOfSixCol(AggregateReportDto.NUMERATOR),
		LayoutUtil.oneOfSixCol(AggregateReportDto.DENOMINATOR),
		LayoutUtil.oneOfSixCol(AggregateReportDto.PROPORTION));

	private TextField caseField;
	private TextField labField;
	private TextField deathField;
	private TextField numeratorField;
	private TextField denominatorField;
	private TextField proportionField;

	public AggregateReportEditForm(String disease, boolean fetchdisease, boolean enable) {
		super(AggregateReportDto.class, AggregateReportDto.I18N_PREFIX);

		this.disease = disease;

		this.fetchdisease = fetchdisease;

		this.enable = enable;

		initialized = true;
		addFields();
	}

	@Override
	protected String createHtmlLayout() {
		return HTML_LAYOUT;
	}

	@Override
	protected void addFields() {
		if (!initialized) {
			// vars have to be set first
			return;
		}

		getContent().setWidth(520, Unit.PIXELS);

		Label diseaseLabel = new Label(disease);
		getContent().addComponent(diseaseLabel, DISEASE_LOC);
		if (fetchdisease == true){
			caseField = addField(AggregateReportDto.NEW_CASES);
			caseField.setInputPrompt(I18nProperties.getCaption(Captions.aggregateReportNewCasesShort));
			caseField.setConversionError(I18nProperties.getValidationError(Validations.onlyNumbersAllowed, caseField.getCaption()));
			labField = addField(AggregateReportDto.LAB_CONFIRMATIONS);
			labField.setInputPrompt(I18nProperties.getCaption(Captions.aggregateReportLabConfirmationsShort));
			labField.setConversionError(I18nProperties.getValidationError(Validations.onlyNumbersAllowed, labField.getCaption()));
			deathField = addField(AggregateReportDto.DEATHS);
			deathField.setInputPrompt(I18nProperties.getCaption(Captions.aggregateReportDeathsShort));
			deathField.setConversionError(I18nProperties.getValidationError(Validations.onlyNumbersAllowed, deathField.getCaption()));
			CssStyles.style(CssStyles.CAPTION_HIDDEN, diseaseLabel, caseField, labField, deathField);
		}
		if (fetchdisease == false){
			numeratorField = addField(AggregateReportDto.NUMERATOR);
			numeratorField.setInputPrompt("Numérateur");
			numeratorField.setConversionError(I18nProperties.getValidationError(Validations.onlyNumbersAllowed, numeratorField.getCaption()));
			denominatorField = addField(AggregateReportDto.DENOMINATOR);
			denominatorField.setInputPrompt("Dénominateur");
			denominatorField.setConversionError(I18nProperties.getValidationError(Validations.onlyNumbersAllowed, denominatorField.getCaption()));
			if (enable == false) {
				denominatorField.setInputPrompt("N/A");
				denominatorField.setEnabled(false);
			}
			proportionField = addField(AggregateReportDto.PROPORTION);
			proportionField.setInputPrompt("Auto");
			proportionField.setVisible(false);
			CssStyles.style(CssStyles.CAPTION_HIDDEN, diseaseLabel, numeratorField, proportionField, denominatorField);
		}
	}

	public boolean isValid() {
		boolean res = false;
		if (fetchdisease == true) {
			res = (caseField.getValue().isEmpty() || DataHelper.isParseableInt(caseField.getValue()))
			&& (labField.getValue().isEmpty() || DataHelper.isParseableInt(labField.getValue()))
			&& (deathField.getValue().isEmpty() || DataHelper.isParseableInt(deathField.getValue()));
		}
		if (fetchdisease == false) {
			res = (numeratorField.getValue().isEmpty() || DataHelper.isParseableInt(numeratorField.getValue()))
			&& (denominatorField.getValue().isEmpty() || DataHelper.isParseableInt(denominatorField.getValue()));
		}
		return res;
	}


	public boolean isValidDenominator() {
		boolean res = true;
		if (fetchdisease == false) {
			if (!StringUtils.isBlank(numeratorField.getValue())) {
				if (StringUtils.isBlank(denominatorField.getValue())) {
					if (enable == true) {
						res = false;
					}
				}
			}
		}
		return res;
	}

	public boolean isValidNumerator() {
		boolean value = true;
		if (fetchdisease == false) {
			if (!StringUtils.isBlank(denominatorField.getValue())) {
				if (StringUtils.isBlank(numeratorField.getValue())) {
					if (enable == true) {
						value = false;
					}
				}
			}
		}
		return value;
	}

	public String getDisease() {
		return disease;
	}

	public void setNewCases(int cases) {
		caseField.setValue(String.valueOf(cases));
	}

	public void setLabConfirmations(int labs) {
		labField.setValue(String.valueOf(labs));
	}

	public void setDeaths(int deaths) {
		deathField.setValue(String.valueOf(deaths));
	}

	public void setNumerator(int numerator) {
		numeratorField.setValue(String.valueOf(numerator));
	}

	public void setDenominator(int denominator) {
		denominatorField.setValue(String.valueOf(denominator));
	}

	public void setProportion(double proportion) {
		proportionField.setValue(String.valueOf(proportion));
	}
}
