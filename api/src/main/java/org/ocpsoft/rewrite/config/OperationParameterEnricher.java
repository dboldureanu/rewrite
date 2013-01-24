/*
 * Copyright 2011 <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ocpsoft.rewrite.config;

import org.ocpsoft.common.util.Assert;
import org.ocpsoft.rewrite.param.Parameter;
import org.ocpsoft.rewrite.param.ParameterStore;
import org.ocpsoft.rewrite.param.Parameterized;

/**
 * {@link Visitor} implementation for {@link Operation}s that executes a given callback for all operations that provide
 * a specific parameter.
 * 
 * @author Christian Kaltepoth
 */
class OperationParameterEnricher implements Visitor<Operation>
{

   private final String param;

   private final ParameterEnricher callback;

   public OperationParameterEnricher(String param, ParameterEnricher callback)
   {
      this.param = param;
      this.callback = callback;
   }

   @Override
   @SuppressWarnings({ "rawtypes", "unchecked" })
   public void visit(Operation operation)
   {
      if (operation instanceof Parameterized) {
         ParameterStore store = ((Parameterized) operation).getParameterStore();

         if (store.containsKey(param)) {

            Parameter<?, String> parameter = store.where(param, null);
            Assert.notNull(parameter, "Didn't find parameter [" + param + "] in:" + operation);

            callback.enrich(parameter);

         }
      }

   }

}
