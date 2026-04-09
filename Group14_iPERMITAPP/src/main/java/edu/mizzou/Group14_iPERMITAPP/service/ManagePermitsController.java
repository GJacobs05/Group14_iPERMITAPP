package edu.mizzou.Group14_iPERMITAPP.service;
import edu.mizzou.Group14_iPERMITAPP.model.EnvironmentalPermit;
import edu.mizzou.Group14_iPERMITAPP.repository.EnvironmentalPermitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagePermitsController {

    @Autowired
    private EnvironmentalPermitRepository repository;

    public Double getPermitFee(String permitId){
        EnvironmentalPermit permit = repository.findById(permitId)
                                                .orElse(null);

        if (permit != null){
            return permit.getPermitFee();
        }
        return null;
    }

    public void setApplication(){

    }
}
