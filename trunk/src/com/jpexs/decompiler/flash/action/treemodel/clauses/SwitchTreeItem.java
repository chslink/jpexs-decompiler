/*
 *  Copyright (C) 2010-2013 JPEXS
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jpexs.decompiler.flash.action.treemodel.clauses;

import com.jpexs.decompiler.flash.action.Action;
import com.jpexs.decompiler.flash.action.treemodel.ConstantPool;
import com.jpexs.decompiler.flash.action.treemodel.ContinueTreeItem;
import com.jpexs.decompiler.flash.action.treemodel.TreeItem;
import java.util.ArrayList;
import java.util.List;

public class SwitchTreeItem extends LoopTreeItem implements Block {

   public TreeItem switchedObject;
   public List<TreeItem> caseValues;
   public List<List<TreeItem>> caseCommands;
   public List<TreeItem> defaultCommands;
   public List<Integer> valuesMapping;

   public SwitchTreeItem(Action instruction, long switchBreak, TreeItem switchedObject, List<TreeItem> caseValues, List<List<TreeItem>> caseCommands, List<TreeItem> defaultCommands, List<Integer> valuesMapping) {
      super(instruction, switchBreak, -1);
      this.switchedObject = switchedObject;
      this.caseValues = caseValues;
      this.caseCommands = caseCommands;
      this.defaultCommands = defaultCommands;
      this.valuesMapping = valuesMapping;
   }

   @Override
   public String toString(ConstantPool constants) {
      String ret = "";
      ret += "loopswitch" + loopBreak + ":\r\n";
      ret += hilight("switch(") + switchedObject.toString(constants) + hilight(")") + "\r\n{\r\n";
      for (int i = 0; i < caseCommands.size(); i++) {
         for (int k = 0; k < valuesMapping.size(); k++) {
            if (valuesMapping.get(k) == i) {
               ret += "case " + caseValues.get(k).toString(constants) + ":\r\n";
            }
         }
         ret += Action.INDENTOPEN + "\r\n";
         for (int j = 0; j < caseCommands.get(i).size(); j++) {
            ret += caseCommands.get(i).get(j).toString(constants) + "\r\n";
         }
         ret += Action.INDENTCLOSE + "\r\n";
      }
      if (defaultCommands != null) {
         if (defaultCommands.size() > 0) {
            ret += hilight("default") + ":\r\n";
            ret += Action.INDENTOPEN + "\r\n";
            for (int j = 0; j < defaultCommands.size(); j++) {
               ret += defaultCommands.get(j).toString(constants) + "\r\n";
            }
            ret += Action.INDENTCLOSE + "\r\n";
         }
      }
      ret += hilight("}") + "\r\n";
      ret += ":loop" + loopBreak;
      return ret;
   }

   public List<ContinueTreeItem> getContinues() {
      List<ContinueTreeItem> ret = new ArrayList<ContinueTreeItem>();

      for (List<TreeItem> onecase : caseCommands) {
         for (TreeItem ti : onecase) {
            if (ti instanceof ContinueTreeItem) {
               ret.add((ContinueTreeItem) ti);
            }
            if (ti instanceof Block) {
               ret.addAll(((Block) ti).getContinues());
            }
         }
      }
      if (defaultCommands != null) {
         for (TreeItem ti : defaultCommands) {
            if (ti instanceof ContinueTreeItem) {
               ret.add((ContinueTreeItem) ti);
            }
            if (ti instanceof Block) {
               ret.addAll(((Block) ti).getContinues());
            }
         }
      }
      return ret;
   }
}