/* 
 * Copyright (C) 2016 RankSys http://ranksys.org
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.ranksys.context.pref;

import es.uam.eps.ir.ranksys.fast.preference.IdxPref;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.ranksys.fast.preference.FastPointWisePreferenceData;

/**
 *
 * @author Saúl Vargas (Saul.Vargas@mendeley.com)
 */
public interface ContextFastPreferenceData<U, I, C> extends ContextPreferenceData<U, I, C>, FastPointWisePreferenceData<U, I> {

    @Override
    public Stream<IdxPrefCtx<C>> getUidxPreferences(int uidx);

    @Override
    public Stream<IdxPrefCtx<C>> getIidxPreferences(int iidx);

    @Override
    public default Optional<IdPrefCtx<I, C>> getPreference(U u, I i) {
        Optional<IdxPrefCtx<C>> pref = getPreference(user2uidx(u), item2iidx(i));

        if (pref.isPresent()) {
            return Optional.of(new IdPrefCtx<>(i, pref.get().v, pref.get().cs));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<IdxPrefCtx<C>> getPreference(int uidx, int iidx);

    public class IdxPrefCtx<C> extends IdxPref {

        public List<C> cs;

        public IdxPrefCtx(int idx, double v, List<C> cs) {
            super(idx, v);
            this.cs = cs;
        }

        public IdxPref refill(int idx, double v, List<C> cs) {
            this.idx = idx;
            this.v = v;
            this.cs = cs;
            return this;
        }

    }
}
