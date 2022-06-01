package de.symeda.sormas.ui.reports.aggregate;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import de.symeda.sormas.api.i18n.Strings;
import de.symeda.sormas.api.FacadeProvider;
import de.symeda.sormas.api.i18n.I18nProperties;
import de.symeda.sormas.api.report.AggregateReportCriteria;
import de.symeda.sormas.api.report.AggregatedCaseCountDto;
import de.symeda.sormas.ui.utils.FilteredGrid;
import com.vaadin.ui.Label;
@SuppressWarnings("serial")
public class AggregateReportsIndicatorsGrid extends FilteredGrid<AggregatedCaseCountDto, AggregateReportCriteria> {
	
	public AggregateReportsIndicatorsGrid() {

		super(AggregatedCaseCountDto.class);
		setSizeFull();
		setSelectionMode(SelectionMode.NONE);
		setInEagerMode(true);
		String title = new Label(I18nProperties.getString(Strings.labellibelle)).getValue();

		setColumns(
			AggregatedCaseCountDto.DISEASE,
			AggregatedCaseCountDto.NUMERATOR,
			AggregatedCaseCountDto.DENOMINATOR,
			AggregatedCaseCountDto.PROPORTION);

		for (Column<?, ?> column : getColumns()) {
			if (I18nProperties.getPrefixCaption(AggregatedCaseCountDto.I18N_PREFIX, column.getId().toString(), column.getCaption()).equals("Maladie")
				|| I18nProperties.getPrefixCaption(AggregatedCaseCountDto.I18N_PREFIX, column.getId().toString(), column.getCaption()).equals("Disease")) {
				column.setCaption("ELEMENT");
			} else {
				if (I18nProperties.getPrefixCaption(AggregatedCaseCountDto.I18N_PREFIX, column.getId().toString(), column.getCaption()).equals("Proportion")) {
				column.setCaption("RATIO");
			}
			else {
				column.setCaption(I18nProperties.getPrefixCaption(AggregatedCaseCountDto.I18N_PREFIX, column.getId().toString(), column.getCaption()));
				}
			}
		}

		reload();
	}

	public void reload() {

		ListDataProvider<AggregatedCaseCountDto> dataProvider =
			DataProvider.fromStream(FacadeProvider.getAggregateReportFacade().getIndexList(getCriteria(), false).stream());
		setDataProvider(dataProvider);
		dataProvider.refreshAll();
	}
}
