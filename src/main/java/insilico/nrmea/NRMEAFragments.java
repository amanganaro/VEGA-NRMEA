package insilico.nrmea;

import insilico.core.exception.GenericFailureException;
import insilico.core.exception.InvalidMoleculeException;
import insilico.core.molecule.InsilicoMolecule;
import java.util.ArrayList;

public class NRMEAFragments {
  private final ArrayList<Fragment> fragmentsList;
  
  public NRMEAFragments(ArrayList<Fragment> frags) {
    this.fragmentsList = frags;
  }
  
  public boolean match(InsilicoMolecule mol) throws GenericFailureException {
    try {
      for (Fragment curFrag : this.fragmentsList) {
        if (curFrag.getQuery().matches(mol.GetStructure()))
          return true; 
      } 
      return false;
    } catch (InvalidMoleculeException ex) {
      throw new GenericFailureException("Error during SMARTS matching: " + ex.getMessage());
    } 
  }
}
