package pack.pr231.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pack.pr231.model.RightsRequest;
import pack.pr231.model.User;
import pack.pr231.repository.RightsRequestRepository;
import pack.pr231.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Date;
@Service
public class RightsRequestService {

    @Autowired
    RightsRequestRepository rightsRequestRepository;

    public void add(Integer id) {
        RightsRequest request = new RightsRequest(id, new Date());
        rightsRequestRepository.save(request);
    }
    public Boolean checkById(Integer id) {

        if (rightsRequestRepository.findById(id).equals(null)) {
            return false;
        }
        return true;
    }
    @Transactional
    public void delete(int id) {
        rightsRequestRepository.deleteById(id);
    }


}
