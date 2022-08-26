package de.symeda.sormas.api.report;

import java.io.Serializable;

import de.symeda.sormas.api.Disease;

public class AggregatedCaseCountDto implements Serializable {

	private static final long serialVersionUID = -6857559727281292882L;

	public static final String I18N_PREFIX = "AggregateReport";

	public static final String DISEASE = "disease";
	public static final String NEW_CASES = "newCases";
	public static final String LAB_CONFIRMATIONS = "labConfirmations";
	public static final String DEATHS = "deaths";
	public static final String NUMERATOR = "numerator";
	public static final String DENOMINATOR = "denominator";
	public static final String PROPORTION = "proportion";

	private Disease disease;
	private int newCases;
	private int labConfirmations;
	private int deaths;
	private int numerator;
	private int denominator;
	private double proportion;

	public AggregatedCaseCountDto(Disease disease, int newCases, int labConfirmations, int deaths, int numerator, int denominator, double proportion) {

		this.disease = disease;
		this.newCases = newCases;
		this.labConfirmations = labConfirmations;
		this.deaths = deaths;
		this.numerator = numerator;
		this.denominator = denominator;
		this.proportion = proportion;
	}

	public Disease getDisease() {
		return disease;
	}

	public void setDisease(Disease disease) {
		this.disease = disease;
	}

	public int getNewCases() {
		return newCases;
	}

	public void setNewCases(int newCases) {
		this.newCases = newCases;
	}

	public int getLabConfirmations() {
		return labConfirmations;
	}

	public void setLabConfirmations(int labConfirmations) {
		this.labConfirmations = labConfirmations;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getNumerator() {
		return numerator;
	}

	public void setNumerator(int numerator) {
		this.numerator = numerator;
	}

	public int getDenominator() {
		return denominator;
	}

	public void setDenominator(int denominator) {
		this.denominator = denominator;
	}

	public double getProportion() {
		return proportion;
	}

	public void setProportion(double proportion) {
		this.proportion = proportion;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + deaths;
		result = prime * result + ((disease == null) ? 0 : disease.hashCode());
		result = prime * result + labConfirmations;
		result = prime * result + newCases;
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AggregatedCaseCountDto other = (AggregatedCaseCountDto) obj;
		if (deaths != other.deaths)
			return false;
		if (disease != other.disease)
			return false;
		if (labConfirmations != other.labConfirmations)
			return false;
		if (newCases != other.newCases)
			return false;
		return true;
	}
}
