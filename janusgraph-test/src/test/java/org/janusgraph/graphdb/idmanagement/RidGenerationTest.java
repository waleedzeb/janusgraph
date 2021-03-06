// Copyright 2017 JanusGraph Authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.janusgraph.graphdb.idmanagement;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ConcurrentSkipListSet;

import com.google.common.collect.Sets;
import org.janusgraph.diskstorage.configuration.Configuration;
import org.apache.commons.configuration.BaseConfiguration;
import org.junit.Test;

import com.google.common.base.Preconditions;
import org.janusgraph.diskstorage.common.DistributedStoreManager;

public class RidGenerationTest {

    @Test
    public void testThreadBasedRidGeneration() throws InterruptedException {
        Configuration c = Configuration.EMPTY;
        int n = 8;
        Preconditions.checkArgument(1 < n); // n <= 1 is useless
        Collection<byte[]> rids = Collections.synchronizedSet(new HashSet<byte[]>());
        RidThread[] threads = new RidThread[n];
        
        for (int i = 0; i < n; i++) {
            threads[i] = new RidThread(rids, c);
        }
        
        for (int i = 0; i < n; i++) {
            threads[i].start();
        }
        
        for (int i = 0; i < n; i++) {
            threads[i].join();
        }
        //TODO: rewrite test case in terms of GraphDatabaseConfiguration
        //assertEquals(n, rids.size());
    }
    
    private static class RidThread extends Thread {
        
        private final Collection<byte[]> rids;
        private final Configuration c;
        
        private RidThread(Collection<byte[]> rids, Configuration c) {
            this.rids = rids;
            this.c = c;
        }
        
        @Override
        public void run() {
            //rids.add(DistributedStoreManager.getRid(c));
        }
    };
}
