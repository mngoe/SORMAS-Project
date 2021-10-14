/*
 *  SORMAS® - Surveillance Outbreak Response Management & Analysis System
 *  Copyright © 2016-2021 Helmholtz-Zentrum für Infektionsforschung GmbH (HZI)
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

/* Returns a list of all tables that are missing a _history equivalent */

SELECT t.table_name FROM information_schema."tables" t
WHERE t.table_schema = 'public' AND t.table_name NOT LIKE '%_history'
AND (SELECT COUNT(t_hist.table_name) FROM information_schema."tables" t_hist WHERE concat(t.table_name,'_history') = t_hist .table_name) = 0;
