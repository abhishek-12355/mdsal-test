/*
 * Copyright © 2016 cc and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.netshell.test.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.urn.com.netshell.mdsal.params.xml.ns.yang.mdsal.test.rev150105.MdsalTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MdSalTestProvider {

    private static final Logger LOG = LoggerFactory.getLogger(MdSalTestProvider.class);

    private final DataBroker dataBroker;
    private final RpcProviderRegistry registry;
    private BindingAwareBroker.RpcRegistration<MdsalTestService> service;

    public MdSalTestProvider(final DataBroker dataBroker, final RpcProviderRegistry registry) {
        this.dataBroker = dataBroker;
        this.registry = registry;
    }

    /**
     * Method called when the blueprint container is created.
     */
    public void init() {
        LOG.info("MdSalTestProvider Session Initiated");

        service = registry.addRpcImplementation(
                MdsalTestService.class,
                new MdSalTestServiceImpl(dataBroker)
        );

        try {
            throw new Exception("Testing MDSAL");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            LOG.error("Service Type: " + service.getServiceType().toGenericString());
        }
    }

    /**
     * Method called when the blueprint container is destroyed.
     */
    public void close() {
        LOG.info("MdSalTestProvider Closed");
    }
}