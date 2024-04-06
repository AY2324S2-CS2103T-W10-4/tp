package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MailCommand;
import seedu.address.model.group.Group;

public class MailCommandParserTest {

    private MailCommandParser parser = new MailCommandParser();

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, " grp/TUT", Group.MESSAGE_CONSTRAINTS_KEYWORD);
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        MailCommand expectedMailCommand = new MailCommand(new Group("TUT10"));
        assertParseSuccess(parser, " grp/TUT10", expectedMailCommand);
    }
}
