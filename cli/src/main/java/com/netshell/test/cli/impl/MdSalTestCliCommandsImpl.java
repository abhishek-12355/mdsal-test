/*
 * Copyright © 2016 cc and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.netshell.test.cli.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.netshell.test.cli.api.MdSalTestCliCommands;

public class MdSalTestCliCommandsImpl implements MdSalTestCliCommands {

    private static final Logger LOG = LoggerFactory.getLogger(MdSalTestCliCommandsImpl.class);
    private final DataBroker dataBroker;

    public MdSalTestCliCommandsImpl(final DataBroker db) {
        this.dataBroker = db;
        LOG.info("MdSalTestCliCommandImpl initialized");
    }

    @Override
    public Object testCommand(Object testArgument) {
        return "This is a test implementation of test-command";
    }
}