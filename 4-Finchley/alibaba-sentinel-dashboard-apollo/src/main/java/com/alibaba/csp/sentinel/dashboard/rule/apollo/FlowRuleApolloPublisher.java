/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.dashboard.rule.apollo;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import com.ctrip.framework.apollo.openapi.dto.NamespaceReleaseDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hantianwei@gmail.com
 * @since 1.5.0
 */
@Component("flowRuleApolloPublisher")
public class FlowRuleApolloPublisher implements DynamicRulePublisher<List<FlowRuleEntity>> {

    @Autowired
    private ApolloOpenApiClient apolloOpenApiClient;
    @Autowired
    private Converter<List<FlowRuleEntity>, String> converter;

    @Value("${env:DEV}")
    private String env;

    @Override
    public void publish(String app, List<FlowRuleEntity> rules) throws Exception {
        String flowDataId = "sentinel.flowRules";

        AssertUtil.notEmpty(app, "app name cannot be empty");
        if (rules == null) {
            return;
        }

        // TODO 处理不兼容的字段，spring cloud alibaba 0.2.2版本中实现JSON转换的时候，不会忽略不存在的字段，会导致客户端出现出现异常
        // 可以通过下面这段代码将这些字段不存入Apollo，以避免客户端加载的错误
        for (FlowRuleEntity ruleEntity : rules) {
            ruleEntity.setId(null);
            ruleEntity.setApp(null);
            ruleEntity.setGmtModified(null);
            ruleEntity.setGmtCreate(null);
            ruleEntity.setIp(null);
            ruleEntity.setPort(null);
        }
        // 请视情况使用

        OpenItemDTO openItemDTO = new OpenItemDTO();
        openItemDTO.setKey(flowDataId);
        openItemDTO.setValue(converter.convert(rules));
        openItemDTO.setComment("modify by sentinel-dashboard");
        openItemDTO.setDataChangeCreatedBy("apollo");
        apolloOpenApiClient.createOrUpdateItem(app, env, "default", "application", openItemDTO);

        // Release configuration
        NamespaceReleaseDTO namespaceReleaseDTO = new NamespaceReleaseDTO();
        namespaceReleaseDTO.setEmergencyPublish(true);
        namespaceReleaseDTO.setReleaseComment("release by sentinel-dashboard");
        namespaceReleaseDTO.setReleasedBy("apollo");
        namespaceReleaseDTO.setReleaseTitle("release by sentinel-dashboard");
        apolloOpenApiClient.publishNamespace(app, env, "default", "application", namespaceReleaseDTO);
    }
}
