/*
 * Copyright © 2016 cc and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.netshell.test.impl;

import org.opendaylight.yang.gen.v1.urn.com.netshell.mdsal.params.xml.ns.yang.mdsal.test.rev150105.MdsalTestService;
import org.opendaylight.yang.gen.v1.urn.com.netshell.mdsal.params.xml.ns.yang.mdsal.test.rev150105.SayHelloInput;
import org.opendaylight.yang.gen.v1.urn.com.netshell.mdsal.params.xml.ns.yang.mdsal.test.rev150105.SayHelloOutput;
import org.opendaylight.yang.gen.v1.urn.com.netshell.mdsal.params.xml.ns.yang.mdsal.test.rev150105.SayHelloOutputBuilder;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;

import java.util.concurrent.Future;


/**
 * @author ASHEKHA
 * @since 5/22/2017.
 */
public class MdSalTestServiceImpl implements MdsalTestService {
    @Override
    public Future<RpcResult<SayHelloOutput>> sayHello(SayHelloInput input) {
        SayHelloOutputBuilder builder = new SayHelloOutputBuilder();
        builder.setResponse("Hello World, " + input.getName());
        return RpcResultBuilder.success(builder.build()).buildFuture();
    }
}
