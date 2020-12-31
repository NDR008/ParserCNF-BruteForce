import computation.contextfreegrammar.*;
import computation.parser.*;
import computation.parsetree.*;
import computation.derivation.*;

import java.util.*;

public class Parser implements IParser {
  private ArrayList<Derivation> classDerivations = new ArrayList<>();
  private final Word emptyWord = new Word();
  private boolean finalResult = false;

/* Brute Force */
  private void mainLogic(ContextFreeGrammar cfg, Word w) {
    final boolean debug = false; // for debug purposes print lines
    final boolean debug2 = false; // for debug purposes print lines
    /* Word Related */
    int n = w.length();
    int steps = 2 * n - 1;
    /* Language Related */
    List<Rule> rules = cfg.getRules(); // hold all rules
    Word startVar = new Word(cfg.getStartVariable()); // initial variable
    Word rootWord; // Word to hold the last word from a previous derivation
    ArrayList<Derivation> derivations = new ArrayList<>(); // list to hold all
    ArrayList<Derivation> finalDerivations = new ArrayList<>(); // list to hold all
    ArrayList<Derivation> perfectDerivations = new ArrayList<>(); // list to hold all
    derivations.add(new Derivation(startVar)); // create the -1 step (initial variable)
    Word ruleVariable; // will be used to hold a rule LHS
    Word ruleExpansion; // will be used to hold a rule RHS
    Word singleSymbolAsWord; // will be used to convert a symbol to a word
    Word wordAfterStep; // will be used to catch the output of replace()

    if (debug) {
      System.out.println(cfg.toString());
      System.out.println("----------");
      System.out.println("Word: " + w.toString());
    }

    boolean found = false; // local variable to store "in language?" result

    if (n == 0){
      for (Rule rule : rules){
        if ( new Word(rule.getVariable()).equals(startVar) && rule.getExpansion().equals(emptyWord) )
        {
          finalResult = true;
          return;
        }
      }
      finalResult = false;
      return;
    }

    int index = 0; // will be used to know at what position to replace()
    for (int i = 1; i <= steps; i++) {
      for (Derivation derivationCell : derivations) {
        rootWord = derivationCell.getLatestWord();
        int rootWordSize = rootWord.length(); // when we get a word...
        if (isWordOnlyTerminals(cfg, rootWord)) { continue; } //cannot process such a word
        for (index=0; index<rootWordSize; index++) { // otherwise look for the first variable
          singleSymbolAsWord = new Word(rootWord.get(index));
          if (!isWordOnlyTerminals(cfg, singleSymbolAsWord)) {
            break;
          }
        }

        singleSymbolAsWord = new Word(rootWord.get(index)); // convert the symbol into a word
        for (Rule rule : rules) {    // let's check each rule
          ruleVariable = new Word(rule.getVariable());
          if (ruleVariable.equals(singleSymbolAsWord)) { // yeah! LHS matches the symbol
            ruleExpansion = rule.getExpansion();
            if (ruleExpansion.equals(emptyWord)) {continue;} // we can skip an empty rule - we should skip
            wordAfterStep = rootWord.replace(index, ruleExpansion);
            Derivation deriv = new Derivation(derivationCell);
            deriv.addStep(wordAfterStep, rule, index);
            perfectDerivations.add(deriv);
            if (debug2) { System.out.println(rootWord + " | " + rule.toString() + " | " + wordAfterStep);}
            if (wordAfterStep.equals(w) && i == steps) {
              finalDerivations.add(deriv);
              found = true; // we could break but, best to find more derivations in case of ambiguity
            }
          }
        }
      }
      derivations = new ArrayList<>(perfectDerivations);
      perfectDerivations.clear();
    }

    classDerivations = finalDerivations;

    if (debug) { PrintDerivations(finalDerivations); }
    finalResult =  found;
    return; // not really needed
  }

  public boolean isInLanguage(ContextFreeGrammar cfg, Word w) {
    mainLogic(cfg, w); // run the main logic to set class attributes;
    return finalResult;
    //return generateParseTree(cfg, w) != null;// this would work but wasteful...
  }

  public ParseTreeNode generateParseTree(ContextFreeGrammar cfg, Word w) {
    mainLogic(cfg, w); // run the main logic to set class attributes;
    if (!finalResult) { return null; }
    if (w.equals(emptyWord)) { return ParseTreeNode.emptyParseTree(cfg.getStartVariable()); }
    Stack<ParseTreeNode> bufferStack = new Stack<>();

      Derivation firstSolution = classDerivations.get(0);
      Iterator<Step> reversedSolution = firstSolution.iterator();

    while (reversedSolution.hasNext()) { // this final word must be made of single children
      Step incStep = reversedSolution.next();
      Rule incRule = incStep.getRule();
      if (incRule == null) { break; }
      Variable LHS = incRule.getVariable();
      Word RHS = incRule.getExpansion();

      if (isWordOnlyTerminals(cfg, RHS)){
        ParseTreeNode tempNode = (new ParseTreeNode(RHS.get(0))); // we know RHS is a terminal so 1 Symbol
        bufferStack.push(new ParseTreeNode(LHS, tempNode));
      }

      else if (RHS.length() > 1){ // 2 children time
        bufferStack.push(new ParseTreeNode(LHS, bufferStack.pop(), bufferStack.pop()));
      }
    }
    return bufferStack.pop();
  }

  // this can check if a single character is a terminal or if a word is made purely of terminals
  public boolean isWordOnlyTerminals(ContextFreeGrammar cfg, Word w) {
    Set<Terminal> terminals = cfg.getTerminals();
    for (Symbol x : w) {
      if (!terminals.contains(x)) {
        return false;
      }
    }
    return true;
  }

  // Debug related method
  public void PrintDerivations(ArrayList<Derivation> derivations) {
    for (Derivation d : derivations) {
      System.out.println(d);
      for (Step s : d) {
        System.out.println(s.toString());
      }
    }
  }

  // Debug related method
  public ArrayList<Derivation> GetDerivation(){
    if (classDerivations.size() >0)
      return classDerivations;
    else {
      return null;
    }
  }
}