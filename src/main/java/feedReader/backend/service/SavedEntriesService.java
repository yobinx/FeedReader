package feedReader.backend.service;
import feedReader.backend.entity.SavedEntriesEntity;
import feedReader.backend.repo.SavedEntriesRepo;
import org.springframework.stereotype.Service;

import java.util.List;

/* Service to manage the db: finding all entries and  adding a new entry */
@Service
public class SavedEntriesService
{
    private final SavedEntriesRepo savedEntriesRepo;

    public SavedEntriesService(SavedEntriesRepo savedEntriesRepo)
    {
        this.savedEntriesRepo = savedEntriesRepo;
    }

    //returns all entries in the db
    public List<SavedEntriesEntity> findAll(){
        return savedEntriesRepo.findAll();
    }

    //add a new entry to the db
    public void addEntryToDb(SavedEntriesEntity entry){
        savedEntriesRepo.save(entry);
    }

    //delete entry from the db
    public void deleteEntryFromDb(SavedEntriesEntity entry){
        savedEntriesRepo.delete(entry);
    }

}
