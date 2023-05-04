package insilico.nrmea.model.tr;

import insilico.core.molecule.InsilicoMolecule;
import insilico.nrmea.NRMEAFragments;
import insilico.nrmea.NRMEAModel;

public class THReceptorAlpha extends NRMEAModel {
  protected NRMEAFragments typeOneFragmentList = null;
  
  protected NRMEAFragments typeTwoFragmentList = null;
  
  protected NRMEAFragments typeOneOneFragmentList = null;
  
  protected NRMEAFragments typeTwoOneFragmentList = null;
  
  protected NRMEAFragments typeOneOneOneFragmentList = null;
  
  private static final String TYPE_1 = "type1";
  
  private static final String TYPE_2 = "type2";
  
  private static final String TYPE_11 = "type1-1";
  
  private static final String TYPE_21 = "type2-1";
  
  private static final String TYPE_111 = "type1-1-1";
  
  public THReceptorAlpha() throws Exception {
    super("/nrmea/fragments_alpha.txt");
  }
  
  protected int calculatePrediction(InsilicoMolecule mol) throws Exception {
    int retval = 4;
    boolean isprimary = this.PrimaryFragsList.match(mol);
    if (!isprimary)
      return retval; 
    boolean istype1 = this.typeOneFragmentList.match(mol);
    if (istype1) {
      boolean istype11 = this.typeOneOneFragmentList.match(mol);
      if (istype11) {
        boolean istype111 = this.typeOneOneOneFragmentList.match(mol);
        if (istype111) {
          retval = 1;
        } else {
          retval = 2;
        } 
      } 
    } else {
      boolean istype21 = this.typeTwoOneFragmentList.match(mol);
      if (istype21)
        retval = 1; 
    } 
    return retval;
  }
  
  protected void initFragsList() throws Exception {
    if (this.typeOneFragmentList == null)
      this.typeOneFragmentList = new NRMEAFragments(getFragmentListOSubType("type1")); 
    if (this.typeTwoFragmentList == null)
      this.typeTwoFragmentList = new NRMEAFragments(getFragmentListOSubType("type2")); 
    if (this.typeOneOneFragmentList == null)
      this.typeOneOneFragmentList = new NRMEAFragments(getFragmentListOSubType("type1-1")); 
    if (this.typeTwoOneFragmentList == null)
      this.typeTwoOneFragmentList = new NRMEAFragments(getFragmentListOSubType("type2-1")); 
    if (this.typeOneOneOneFragmentList == null)
      this.typeOneOneOneFragmentList = new NRMEAFragments(getFragmentListOSubType("type1-1-1")); 
  }
}
