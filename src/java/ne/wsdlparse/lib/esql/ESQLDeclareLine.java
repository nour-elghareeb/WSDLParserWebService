package ne.wsdlparse.lib.esql;

import java.util.Locale;

import ne.wsdlparse.lib.esql.constant.ESQLDataType;
import ne.wsdlparse.lib.utility.ConsoleStyle;

public class ESQLDeclareLine extends ESQLLine {
    private String param;
    private ESQLDataType type;
    private String defaultValue = "";

    ESQLDeclareLine(String paramName, ESQLDataType type, String defaultValue) {
        super();
        this.param = paramName;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    ESQLDeclareLine(String paramName, ESQLDataType type) {
        super();
        this.param = paramName;
        this.type = type;
    }

    @Override
    String generate(boolean useColors) {
        return String.format(Locale.getDefault(), "DECLARE %s %s '%s';", this.param, this.type.getValue(),
                this.defaultValue);
    }

    @Override
    public void print() {
        String line = String.format(Locale.getDefault(), "%s %s %s '%s';",
                ConsoleStyle.addTextColor("DECLARE", ConsoleStyle.Color.YELLOW),
                ConsoleStyle.style(this.param, ConsoleStyle.Style.BOLD),
                ConsoleStyle.addTextColor(this.type.getValue(), ConsoleStyle.Color.BLUE), this.defaultValue);
        System.out.println(line);
    }
}