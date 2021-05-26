/*
 * SORMAS® - Surveillance Outbreak Response Management & Analysis System
 * Copyright © 2016-2021 Helmholtz-Zentrum für Infektionsforschung GmbH (HZI)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.sormas.e2etests.pages.application.contacts;

import org.openqa.selenium.By;

public class PersonContactDetailsPage {

  public static final By PERSON_CONTACT_DETAILS_POPUP = By.cssSelector(".v-window  .popupContent");
  public static final By TYPE_OF_CONTACT_DETAILS_COMBOBOX =
      By.cssSelector(".v-window #personContactDetailType > div");
  public static final By CONTACT_INFORMATION_INPUT =
      By.cssSelector(".v-window #contactInformation");
  public static final By ADDITIONAL_INFORMATION_INPUT =
      By.cssSelector(".v-window #additionalInformation");
  public static final By DONE_BUTTON = By.cssSelector(".v-window #commit");
}
