package org.jboss.windup.web.keycloaktool.options;

import net.sourceforge.argparse4j.inf.Argument;
import net.sourceforge.argparse4j.inf.ArgumentAction;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;

import java.util.Map;

/**
 * @author <a href="mailto:jesse.sightler@gmail.com">Jesse Sightler</a>
 */
public interface StoreAction extends ArgumentAction {
    void store(String name, Object value);

    @Override
    default void run(ArgumentParser parser, Argument arg, Map<String, Object> attrs, String flag, Object value) throws ArgumentParserException {
        store(arg.getDest(), value);
    }

    @Override
    default void onAttach(Argument arg) {

    }

    @Override
    default boolean consumeArgument() {
        return true;
    }
}
