#!/bin/bash

cat <<YAML
apiVersion: apps/v1beta1
kind: Deployment
metadata:
  name: rest-aggregator
spec:
  replicas: 1
  strategy:
    type: RollingUpdate
  template:
    metadata:
      labels:
        app: rest-aggregator
    spec:
      containers:
        - name: rest-aggregator
          image: gcr.io/$GCP_PROJECT/rest-aggregator:latest
          imagePullPolicy: Always
          resources:
            limits:
              cpu: "1"
            requests:
              cpu: "100m"
          ports:
            - containerPort: 8080
          env:
            - name: datasource_a_url
              value: "http://rest-content-a:8080"
            - name: datasource_b_url
              value: "http://rest-content-b:8080"
            - name: content_type_a
              value: "$CONTENT_TYPE_A"
            - name: content_type_b
              value: "$CONTENT_TYPE_B"
            - name: foobar
              value: "$(date +%s)"

---
apiVersion: v1
kind: Service
metadata:
  name: rest-aggregator
spec:
  type: NodePort
  selector:
    app: rest-aggregator
  ports:
   - port: 8080
     targetPort: 8080
     protocol: TCP
---

YAML