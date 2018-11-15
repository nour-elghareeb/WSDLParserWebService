package ne.wsdlparse.lib.esql;

import ne.wsdlparse.lib.esql.constant.ESQLSource;

public abstract class ESQLLine {

    protected ESQLSource source;
    protected boolean useReference;

    ESQLLine() {

    }

    abstract String generate(boolean useColors);

    abstract public void print();

    public void setSource(ESQLSource source) {
        this.source = source;
    }

    public void useReferences(boolean useReference) {
        this.useReference = useReference;
    }
}
