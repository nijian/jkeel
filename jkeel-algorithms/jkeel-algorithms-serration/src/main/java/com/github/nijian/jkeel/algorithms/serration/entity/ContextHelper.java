package com.github.nijian.jkeel.algorithms.serration.entity;

import com.github.nijian.jkeel.algorithms.AlgorithmContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class ContextHelper {

    private static Logger logger = LoggerFactory.getLogger(ContextHelper.class);

    private ContextHelper() {
    }

    public static void populate(final AlgorithmContext ac, final Context context, final Map<String, LayoutOutputInstance> itemOutMap,
                                final Map<String, ItemInstanceAnchor> itemLocationMap, final Map<String, List<BigDecimal>> outputMap) {
        LayoutTemplate layoutTemplate = (LayoutTemplate) ac.getTemplate();
        final String cid = layoutTemplate.getCid();

        LayoutTemplateInstance layoutTemplateInstance = new LayoutTemplateInstance(context);
        context.setLayoutTemplateInstance(layoutTemplateInstance);
        List<LayoutInstance> layoutInstances = layoutTemplateInstance.getLayoutInstances();

        layoutTemplate.getLayouts().stream().forEach(layout ->
                {
                    LayoutInstance layoutInstance = new LayoutInstance(context, cid, layout, ac.getCache());
                    layoutInstances.add(layoutInstance);
                    LayoutOutputInstance layoutOutputInstance = layoutInstance.getLayoutOutputInstance();

                    List<ParallelAreaInstance> pAreaIList = new ArrayList<>();
                    layoutInstance.setParallelAreaInstances(pAreaIList);

                    layout.getParallelAreas().stream().forEach(pArea ->
                            {
                                ParallelAreaInstance pAreaI = new ParallelAreaInstance(pArea);
                                pAreaIList.add(pAreaI);

                                List<ItemInstanceAnchor> itemIAList = new ArrayList<>();
                                pAreaI.setItemInstanceAnchors(itemIAList);

                                pArea.getItems().stream().forEach(item ->
                                        {
                                            if (item.isOut()) {
                                                itemOutMap.put(item.getName(), layoutOutputInstance);
                                                layoutOutputInstance.getMap().put(item.getName(), new ArrayList<>());
                                            }

                                            ItemInstanceAnchor itemInstanceAnchor = new ItemInstanceAnchor(item, layoutInstance.getItemGroupCount());
                                            itemLocationMap.put(item.getName(), itemInstanceAnchor);
                                            itemIAList.add(itemInstanceAnchor);


                                            for (int i = 0; i < itemInstanceAnchor.getItemInstances().size(); i++) {
                                                itemInstanceAnchor.getItemInstances().get(i).setIndex(i);
                                            }

                                        }
                                );
                            }
                    );
                }
        );

        if (layoutTemplate.getOutputFields() != null) {
            String[] outputNameArray = layoutTemplate.getOutputFields().split(",");
            Arrays.stream(outputNameArray).forEach(outputName ->
                    outputMap.put(outputName, new ArrayList<BigDecimal>())
            );
        }

    }
}
