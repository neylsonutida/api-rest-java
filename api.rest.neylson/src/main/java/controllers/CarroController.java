package controllers;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import model.Carro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import repositories.CarroRepository;

@RestController
public class CarroController {
    @Autowired //quando for necessário instancia pra mim
    CarroRepository carroRepository;
    
    @RequestMapping(value="/carros", method = RequestMethod.GET)
        public List<Carro> getCarros(){
        return carroRepository.findAll();
    }
        
    @RequestMapping(value="/carro", method=RequestMethod.POST)
        public Carro post(@Valid @RequestBody Carro carro){
        return carroRepository.save(carro);
    }
    
    @RequestMapping (value="/carro/(id)", method=RequestMethod.PUT)
         public ResponseEntity<Carro> put(@PathVariable(value="id") long id, @Valid @RequestBody Carro novoCarro){
        Optional<Carro> carroVelho =
                carroRepository.findById(id);
        if(carroVelho.isPresent()){
            Carro carro = carroVelho.get();
            carro.setNome(novoCarro.getNome());
            carro.setMarca(novoCarro.getMarca());
            carroRepository.save(carro);
            return new ResponseEntity<>(carro, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @RequestMapping(value="/carro/(id)", method=RequestMethod.DELETE)
         public ResponseEntity<Object> delete(@PathVariable(value="id") long id){
        Optional<Carro> carro
                = carroRepository.findById(id);
        if(carro.isPresent()){
            carroRepository.delete(carro.get());
            return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    
    @RequestMapping(value="/carro/(id)", method=RequestMethod.GET)
    public ResponseEntity<Carro> getById(
    @PathVariable(value="id") long id){
        Optional<Carro> carro =
                carroRepository.findById((id));
        if(carro.isPresent()){
            return new ResponseEntity<>(carro.get(), HttpStatus.OK);
            }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

