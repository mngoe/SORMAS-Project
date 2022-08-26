
package de.symeda.sormas.ui.dashboard.surveillance.components.disease.burden;
import de.symeda.sormas.ui.dashboard.PythogenTestsGrid;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.v7.shared.ui.grid.HeightMode;

public class PythogentBurdenComponent extends VerticalLayout {

	private static final long serialVersionUID = 6582975657305031105L;
	private PythogenTestsGrid grid;

	public PythogentBurdenComponent() {
		grid = new PythogenTestsGrid();
		grid.setHeightMode(HeightMode.ROW);
		grid.setWidth(100, Unit.PERCENTAGE);

		// layout
		setWidth(100, Unit.PERCENTAGE);
		addComponent(grid);
		setMargin(new MarginInfo(true, true, false, true));
		setSpacing(false);
		setExpandRatio(grid, 1);
	}
}