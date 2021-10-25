package de.symeda.sormas.api.infrastructure;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

import de.symeda.sormas.api.BaseFacade;
import de.symeda.sormas.api.EntityDto;
import de.symeda.sormas.api.InfrastructureDataReferenceDto;
import de.symeda.sormas.api.utils.criteria.BaseCriteria;

@Remote
public interface InfrastructureBaseFacade<DTO extends EntityDto, INDEX_DTO extends Serializable, REF_DTO extends InfrastructureDataReferenceDto, CRITERIA extends BaseCriteria>
	extends BaseFacade<DTO, INDEX_DTO, REF_DTO, CRITERIA> {

	// todo investigate if we can move the save function up the hierarchy
	DTO save(DTO dto, boolean allowMerge);

	List<REF_DTO> getByExternalId(String externalId, boolean includeArchivedEntities);

}
