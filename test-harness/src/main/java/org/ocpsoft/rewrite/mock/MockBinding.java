package org.ocpsoft.rewrite.mock;

import org.ocpsoft.rewrite.bind.BindingBuilder;
import org.ocpsoft.rewrite.context.EvaluationContext;
import org.ocpsoft.rewrite.event.Rewrite;

public class MockBinding extends BindingBuilder
{
   private boolean submitted;
   private boolean validated;
   private boolean converted;
   private boolean extracted;
   private Object value;

   private Object returnOnSubmit = false;

   public MockBinding()
   {
      value = new Object();
   }

   public MockBinding(Object value)
   {
      this.value = value;
   }

   public MockBinding(Object value, Object returnOnSubmit)
   {
      this.returnOnSubmit = returnOnSubmit;
      this.value = value;
   }

   @Override
   public boolean validates(final Rewrite event, final EvaluationContext context, final Object value)
   {
      validated = true;
      return true;
   }

   @Override
   public Object convert(final Rewrite event, final EvaluationContext context, final Object value)
   {
      converted = true;
      return value;
   }

   public boolean isConverted()
   {
      return converted;
   }

   public boolean isSubmitted()
   {
      return submitted;
   }

   public boolean isValidated()
   {
      return validated;
   }

   public boolean isExtracted()
   {
      return extracted;
   }

   @Override
   public Object submit(final Rewrite event, final EvaluationContext context, final Object value)
   {
      if (value.getClass().isArray())
      {
         Object[] temp = (Object[]) value;
         if (temp.length > 0)
         {
            this.value = temp[0];
         }
      }
      else
         this.value = value;

      submitted = true;

      return returnOnSubmit;
   }

   @Override
   public Object retrieve(final Rewrite event, final EvaluationContext context)
   {
      extracted = true;
      return value;
   }

   @Override
   public boolean supportsRetrieval()
   {
      return true;
   }

   @Override
   public boolean supportsSubmission()
   {
      return true;
   }

   public Object getBoundValue()
   {
      return value;
   }

   @Override
   public String toString()
   {
      return "MockBinding [submitted=" + submitted + ", validated=" + validated + ", converted=" + converted
               + ", extracted=" + extracted + ", value=" + value + ", returnOnSubmit=" + returnOnSubmit + "]";
   }

}