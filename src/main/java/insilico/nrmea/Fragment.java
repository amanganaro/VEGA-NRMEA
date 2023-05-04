package insilico.nrmea;

import insilico.core.exception.InitFailureException;
import org.openscience.cdk.isomorphism.Pattern;
import org.openscience.cdk.smarts.SmartsPattern;

public class Fragment {
  private final String id;
  private final String fragmentSMARTS;
  private final String type;
  private final String subtype;
  private final Pattern query;


  public Fragment(String id, String fragmentSMARTS, String type, String subtype) throws InitFailureException {
    this.id = id;
    this.fragmentSMARTS = fragmentSMARTS;
    this.type = type;
    this.subtype = subtype;
    try {
      this.query = (Pattern)SmartsPattern.create(fragmentSMARTS).setPrepare(false);
    } catch (Exception e) {
      throw new InitFailureException("Unable to init SMARTS: " + fragmentSMARTS);
    } 
  }
  
  public String getId() {
    return this.id;
  }
  
  public String getFragmentSMARTS() {
    return this.fragmentSMARTS;
  }
  
  public String getType() {
    return this.type;
  }
  
  public String getSubtype() {
    return this.subtype;
  }
  
  public Pattern getQuery() {
    return this.query;
  }
  
  public String toString() {
    return "Fragment[id " + this.id + " frag " + this.fragmentSMARTS + " type " + this.type + " subtype " + this.subtype;
  }
}
