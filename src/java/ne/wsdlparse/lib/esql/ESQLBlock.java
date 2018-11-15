package ne.wsdlparse.lib.esql;

import java.util.ArrayList;
import java.util.HashSet;

import ne.wsdlparse.lib.WSDLManagerRetrieval;
import ne.wsdlparse.lib.constant.ESQLVerbosity;
import ne.wsdlparse.lib.esql.constant.ESQLDataType;
import ne.wsdlparse.lib.esql.constant.ESQLSource;

public class ESQLBlock {
    private WSDLManagerRetrieval manager;
    private ArrayList<ESQLLine> elementsLines;
    private ArrayList<ESQLLine> nsDeclarations;
    private HashSet<String> prefixes;
    private boolean lastWasEmpty = false;
    private ESQLVerbosity[] verbosities = ESQLVerbosity.values();;

    public ESQLBlock(WSDLManagerRetrieval manager) {
        this.manager = manager;
        this.elementsLines = new ArrayList<ESQLLine>();
        this.nsDeclarations = new ArrayList<ESQLLine>();
        this.prefixes = new HashSet<String>();
    }

    void addLine(ESQLLine line) {
        this.elementsLines.add(line);
        this.lastWasEmpty = false;
    }

    void addPrefix(String prefix) {
        if (prefix == null)
            return;
        this.prefixes.add(prefix);
    }

    private void generateNSLines() {
        this.nsDeclarations.clear();
        for (String prefix : prefixes) {
            this.nsDeclarations
                    .add(new ESQLDeclareLine(prefix, ESQLDataType.NAMESPACE, this.manager.getNamespaceURI(prefix)));
        }
    }

    public void printOutputSetters() {
        this.printESQL(ESQLSource.OUTPUT, true);
    }
    private void printESQL(ESQLSource source, boolean useRef){
        this.generateNSLines();
        for (ESQLLine line : this.nsDeclarations) {
            line.setSource(source);
            line.useReferences(useRef);
            line.print();
        }
        for (ESQLLine line : this.elementsLines) {
            line.setSource(source);
            line.useReferences(useRef);
            if (line instanceof ESQLCommentLine) {
                for (ESQLVerbosity verbosity : this.verbosities) {
                    if (verbosity.equals(((ESQLCommentLine) line).getVerbosity())) {
                        line.print();
                        break;
                    }
                }
                continue;
            }
            line.print();

        }
    }
    public void printInputVariables() {
        this.printESQL(ESQLSource.INPUT, false);
    }
    public void printInputReferences() {
        this.printESQL(ESQLSource.INPUT, true);
    }

    public String generate(ESQLSource type) {
        this.generateNSLines();
        StringBuilder builder = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        for (ESQLLine line : this.nsDeclarations) {
            builder.append(line.generate(false));
            builder.append(newLine);
        }
        for (ESQLLine line : this.elementsLines) {
            if (line instanceof ESQLCommentLine) {
                for (ESQLVerbosity verbosity : this.verbosities) {
                    if (verbosity.equals(((ESQLCommentLine) line).getVerbosity())) {
                        break;
                    }
                }
                continue;
            }
            builder.append(line.generate(false));
            builder.append(newLine);
        }
        return builder.toString();
    }

    public void addEmptyLine(boolean allowMultiSuccessiveEmpty) {
        if (!this.lastWasEmpty || (allowMultiSuccessiveEmpty && this.lastWasEmpty))
            this.elementsLines.add(new ESQLCommentLine(ESQLVerbosity.EMPTY, null));
        this.lastWasEmpty = true;
    }

    public void clear() {
        this.elementsLines.clear();
        this.prefixes.clear();
        this.nsDeclarations.clear();
        this.lastWasEmpty = false;
    }

    public void setVerbosity(ESQLVerbosity... verbosity) {
        this.verbosities = verbosity;
    }
}