/*
 * Copyright © 2016 cc and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.netshell.test.impl;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.ReadFailedException;
import org.opendaylight.yang.gen.v1.urn.com.netshell.mdsal.params.xml.ns.yang.mdsal.test.rev150105.*;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;


/**
 * @author ASHEKHA
 * @since 5/22/2017.
 */
public class MdSalTestServiceImpl implements MdsalTestService {

    private static final Logger LOG = LoggerFactory.getLogger(MdSalTestServiceImpl.class);

    private final DataBroker dataBroker;
    private final Map<String, InstanceIdentifier<StudentContainer>> instanceIdentifierMap = new HashMap<>();

    public MdSalTestServiceImpl(DataBroker dataBroker) {
        this.dataBroker = dataBroker;
    }

    @Override
    public Future<RpcResult<SayHelloOutput>> sayHello(SayHelloInput input) {
        SayHelloOutputBuilder builder = new SayHelloOutputBuilder();
        builder.setResponse("Hello World, " + input.getName());
        return RpcResultBuilder.success(builder.build()).buildFuture();
    }

    @Override
    public Future<RpcResult<GetStudentOutput>> getStudent(GetStudentInput input) {

        final ReadOnlyTransaction tx = dataBroker.newReadOnlyTransaction();
        GetStudentOutput studentOutput = null;
        LOG.error("TXREAD Identifier" + tx.getIdentifier());
        try {
            final Student student = tx.read(LogicalDatastoreType.OPERATIONAL,
                    instanceIdentifierMap.get(input.getName())).checkedGet().get();

            studentOutput = new GetStudentOutputBuilder()
                    .setName(student.getName())
                    .setSex(student.getSex())
                    .build();
        } catch (ReadFailedException e) {
            LOG.error(e.getMessage(), e);
        }

        return Futures.immediateFuture(RpcResultBuilder.success(studentOutput).build());
    }

    @Override
    public Future<RpcResult<Void>> saveStudent(SaveStudentInput input) {
        final WriteTransaction tx = dataBroker.newWriteOnlyTransaction();
        instanceIdentifierMap.put(input.getName(), InstanceIdentifier.builder(StudentContainer.class).build());
        tx.put(LogicalDatastoreType.OPERATIONAL, instanceIdentifierMap.get(input.getName()),
                new StudentContainerBuilder()
                        .setName(input.getName())
                        .setSex(input.getSex())
                        .build()
        );

        Futures.addCallback(tx.submit(), new FutureCallback<Void>() {
            @Override
            public void onSuccess(final Void result) {
                LOG.error("Successfully set ToasterStatus to Up");
                notifyCallback(true);
            }

            @Override
            public void onFailure(final Throwable failure) {
                // We shouldn't get an OptimisticLockFailedException (or any ex) as no
                // other component should be updating the operational state.
                LOG.error("Failed to update toaster status", failure);

                notifyCallback(false);
            }

            void notifyCallback(final boolean result) {
                LOG.error("Notification Received " + result);
            }
        });

        return Futures.immediateFuture(RpcResultBuilder.<Void>success().build());

    }
}
