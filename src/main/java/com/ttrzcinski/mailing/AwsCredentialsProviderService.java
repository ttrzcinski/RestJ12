package com.ttrzcinski;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.annotation.Value;
import javax.inject.Singleton;

@Singleton
@Requires(condition = AwsCredentialsProviderCondition.class)
public class AwsCredentialsProviderService implements AWSCredentialsProvider {

    protected final String accessKey;
    protected final String secretKey;

    public AwsCredentialsProviderService(@Value("${AWS_ACCESS_KEY_ID:none}") String accessKeyEnv,
                                  @Value("${AWS_SECRET_KEY:none}") String secretKeyEnv,
                                  @Value("${aws.accesskeyid:none}") String accessKeyProp,
                                  @Value("${aws.secretkey:none}") String secretKeyProp) {
        this.accessKey = accessKeyEnv != null && !accessKeyEnv.equals("none") ? accessKeyEnv : accessKeyProp;
        this.secretKey = secretKeyEnv != null && !secretKeyEnv.equals("none")  ? accessKeyEnv : secretKeyProp;
    }

    @Override
    public AWSCredentials getCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    @Override
    public void refresh() {

    }
}

