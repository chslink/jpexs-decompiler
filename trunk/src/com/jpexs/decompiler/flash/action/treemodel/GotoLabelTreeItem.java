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
package com.jpexs.decompiler.flash.action.treemodel;

import com.jpexs.decompiler.flash.action.Action;
import com.jpexs.decompiler.flash.helpers.Helper;

public class GotoLabelTreeItem extends TreeItem {

   public String label;

   public GotoLabelTreeItem(Action instruction, String label) {
      super(instruction, PRECEDENCE_PRIMARY);
      this.label = label;
   }

   @Override
   public String toString(ConstantPool constants) {
      return hilight("gotoAndStop(\"") + Helper.escapeString(label) + hilight("\")") + ";";
   }
}