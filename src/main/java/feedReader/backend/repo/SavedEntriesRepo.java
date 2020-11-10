package feedReader.backend.repo;

import feedReader.backend.entity.SavedEntriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/* Repository-Interface: where the data from the db is stored */
public interface SavedEntriesRepo extends JpaRepository<SavedEntriesEntity, Long>
{
}
