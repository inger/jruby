/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jruby.compiler.ir.instructions.jruby;

import java.util.Map;
import org.jruby.compiler.ir.Operation;
import org.jruby.compiler.ir.instructions.Instr;
import org.jruby.compiler.ir.operands.Operand;
import org.jruby.compiler.ir.operands.StringLiteral;
import org.jruby.compiler.ir.operands.Variable;
import org.jruby.compiler.ir.representations.InlinerInfo;
import org.jruby.compiler.ir.targets.JVM;
import org.jruby.runtime.Block;
import org.jruby.runtime.DynamicScope;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;

/**
 *
 */
public class IsMethodBoundInstr extends DefinedObjectNameInstr {
    public IsMethodBoundInstr(Variable result, Operand object, StringLiteral name) {
        super(Operation.IS_METHOD_BOUND, result, new Operand[] { object, name });
    }

    @Override
    public Instr cloneForInlining(InlinerInfo inlinerInfo) {
        return new IsMethodBoundInstr((Variable) getResult().cloneForInlining(inlinerInfo), 
                getObject().cloneForInlining(inlinerInfo),
                (StringLiteral) getName().cloneForInlining(inlinerInfo));
    }

    @Override
    public Object interpret(ThreadContext context, DynamicScope currDynScope, IRubyObject self, Object[] temp, Block block) {
        IRubyObject receiver = (IRubyObject) getObject().retrieve(context, self, currDynScope, temp);
        
        return context.runtime.newBoolean(receiver.getMetaClass().isMethodBound(getName().string, false)); 
    }

    @Override
    public void compile(JVM jvm) {
        // no-op right now
    }    
}
